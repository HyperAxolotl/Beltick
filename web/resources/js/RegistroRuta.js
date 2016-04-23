var poly;

function initMap() {
    poly = new google.maps.Polyline({
        strokeColor: '#000000',
        strokeOpacity: 1.0,
        strokeWeight: 3
    });
}

function addLatLng(event) {
    var path = poly.getPath();

    path.push(event.latLng);

    var marker = new google.maps.Marker({
        position: event.latLng,
        title: '#' + path.getLength(),
    });
    PF('map').addOverlay(marker);
    PF('map').addOverlay(poly);
    document.getElementById('mapa').value = google.maps.geometry.encoding.encodePath(poly.getPath());
}
