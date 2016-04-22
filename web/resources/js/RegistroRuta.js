var poly;
var map;

function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 7,
    center: {lat: 19.3241874, lng: -99.1789797}
  });

  poly = new google.maps.Polyline({
    strokeColor: '#000000',
    strokeOpacity: 1.0,
    strokeWeight: 3
  });
  poly.setMap(map);

  map.addListener('click', addLatLng);
}

function addLatLng(event) {
  var path = poly.getPath();
  
  path.push(event.latLng);

  var marker = new google.maps.Marker({
    position: event.latLng,
    title: '#' + path.getLength(),
    map: map
  });
}
