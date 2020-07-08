// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        long duration = request.getDuration();
        if (duration > TimeRange.WHOLE_DAY.duration()) {
            return new ArrayList<TimeRange>();
        }

        Collection<String> mandatoryAttendees = request.getAttendees();
        Collection<String> allAttendees = request.getAllAttendees();

        Collection<Event> mandatoryAttendeeEvents = getEventsForAttendees(mandatoryAttendees, events);
        List<TimeRange> mandatoryAttendeesSlots = getPossibleSlots(mandatoryAttendeeEvents, mandatoryAttendees, duration);

        Collection<Event> allAttendeeEvents = getEventsForAttendees(allAttendees, events);
        List<TimeRange> allAttendeeSlots = getPossibleSlots(allAttendeeEvents, allAttendees, duration);


        if (mandatoryAttendees.isEmpty()) {
            return allAttendeeSlots;
        }
        else if (!allAttendeeSlots.isEmpty()) {
            return allAttendeeSlots;
        }
        else {
            return mandatoryAttendeesSlots;
        }
    }

    public Collection<Event> getEventsForAttendees(Collection<String> attendees, Collection<Event> events) {
        List<Event> filteredEvents = new ArrayList<>();;
        for (Event event : events) {
            Set<String> eventAttendees = event.getAttendees();
            for (String attendee : attendees) {
                if (eventAttendees.contains(attendee)) {
                    filteredEvents.add(event);
                    break;
                }
            }
        }
        return filteredEvents;
    }

    public List<TimeRange> getPossibleSlots(Collection<Event> events, Collection<String> attendees, long duration) {
        List<TimeRange> slots = new ArrayList<TimeRange>();
        int attendeeNumber = attendees.size();

        if (attendeeNumber == 0) {
            slots.add(TimeRange.WHOLE_DAY);
            return slots;
        }
        else {
            int latestEventEnd = TimeRange.START_OF_DAY;
            int start = TimeRange.START_OF_DAY;
            int end = TimeRange.END_OF_DAY;

            for (Event event : events) {
                if (event.getWhen() == TimeRange.WHOLE_DAY) {
                    return new ArrayList<TimeRange>();
                }
                int eventTimeStart = event.getWhen().start();
                int eventTimeEnd = event.getWhen().end();
                if (start == eventTimeStart && end == eventTimeEnd) {
                    return slots;
                }
                else if (eventTimeStart == start && eventTimeEnd < end) {
                    start = eventTimeEnd;
                }
                else if (eventTimeStart > start && eventTimeStart - start >= duration && eventTimeEnd >= end) {
                    slots.add(TimeRange.fromStartEnd(start, eventTimeStart, false));
                    return slots;
                }
                else if (eventTimeStart > start && eventTimeStart - start >= duration && eventTimeEnd < end) {
                    slots.add(TimeRange.fromStartEnd(start, eventTimeStart, false));
                    start = eventTimeEnd;
                }
                latestEventEnd = Math.max(latestEventEnd, eventTimeEnd);
            }

            if (end - latestEventEnd >= duration) {
                slots.add(TimeRange.fromStartEnd(latestEventEnd, end, true));
            }
            return slots;
        }
    }
}
