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

async function getComments() {
    var servletURL = "/data"
    var maxComments = document.getElementById("max-comments").value;
    if (maxComments != null && maxComments >= 1) {
        servletURL = `/data?max-comments=${maxComments}`;
    }
    const response = await fetch(servletURL);
    const comments = await response.json();
	const commentsList = document.getElementById('comment-container');
	commentsList.innerHTML = '';
	comments.forEach(comment => {
		const content = `${comment.comment}`;
		commentsList.appendChild(createListElement(content));
    });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerHTML = "- " + text;
  return liElement;
}

var map;
function initMap() {
  var map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 40.4427, lng: -79.9430 },
    zoom: 16,
    mapTypeId: "satellite"
  });
  map.setTilt(45);

  const cmuMarker = new google.maps.Marker({
    position: {lat: 40.4431, lng: -79.9447},
    map: map,
    title: 'Gates School of Computer Science'
  });

  const cmuInfoWindow =
      new google.maps.InfoWindow({content: 'You can find me here often, at the Gates School of Computer Science!'});
  cmuInfoWindow.open(map, cmuMarker);
}