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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> attendees = request.getAttendees();
    int attendeeNumber = attendees.size();
    ArrayList<TimeRange> slots = new ArrayList<TimeRange>();
    long duration = request.getDuration();

    if (attendeeNumber == 0) {
        slots.add(TimeRange.WHOLE_DAY);
        return slots;
    }
    else if (duration > TimeRange.WHOLE_DAY.duration()) {
        return slots;
    }
    else {
        int eventTimeStart = TimeRange.START_OF_DAY;
        int eventTimeEnd = TimeRange.START_OF_DAY;
        int latestEventEnd = TimeRange.START_OF_DAY;
        int start = TimeRange.START_OF_DAY;
        int end = TimeRange.END_OF_DAY;
        Boolean conflictingAttendees = false;


        for (Event event : events) {
            eventTimeStart = event.getWhen().start();
            eventTimeEnd = event.getWhen().end();
            Set<String> eventAttendees = event.getAttendees();
            conflictingAttendees = false;
            for (String attendee : attendees) {
                if (eventAttendees.contains(attendee)) {
                    conflictingAttendees = true;
                    break;
                }
            }
            if (conflictingAttendees == false) {
                continue;
            }
            else if (start == eventTimeStart && end == eventTimeEnd) {
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
        if (end - latestEventEnd >= duration && conflictingAttendees == true) {
            slots.add(TimeRange.fromStartEnd(latestEventEnd, end, true));
        }
        else if (end - latestEventEnd >= duration && conflictingAttendees == false) {
            slots.add(TimeRange.fromStartEnd(start, end, true));
        }
        return slots;
    }
    }
}
