var poly = new google.maps.Polyline({
    strokeColor: '#DC143C',
    strokeOpacity: 1.0,
    strokeWeight: 3
});
//var mark;

function addLatLng(event) {
    var path = poly.getPath();
    path.push(event.latLng);
   /* var marker = new google.maps.Marker({
        position: event.latLng,
        title: '' + path.getLength(),
    });
    marker.setDraggable(true);
    marker.addListener('dragend', actualizarPoly);
    mark = marker;
    PF('map').addOverlay(marker);*/
    PF('map').addOverlay(poly);
    document.getElementById('mapa').value = google.maps.geometry.encoding.encodePath(poly.getPath());
}

/*function actualizarPoly() {
    var path = poly.getPath();
    var latlng = path.getAt(parseInt(mark.getLabel()) - 1);
    latlng = mark.getPosition();
    PF('map').addOverlay(poly);
    document.getElementById('mapa').value = google.maps.geometry.encoding.encodePath(poly.getPath());
}*/

function borrarUltimo() {
    var path = poly.getPath();
    path.pop();
    document.getElementById('mapa').value = google.maps.geometry.encoding.encodePath(poly.getPath());
}

function borrarTodo() {
    var path = poly.getPath();
    path.clear();
    document.getElementById('mapa').value = google.maps.geometry.encoding.encodePath(poly.getPath());
}