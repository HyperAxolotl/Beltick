var circulo;
var latlng;

function addCirculo(event) {
    if (circulo != null)
        circulo.setMap(null);
    latlng = event.latLng;
    circulo = new google.maps.Circle({
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#FF0000',
        fillOpacity: 0.35,
        center: {lat: latlng.lat(), lng: latlng.lng()},
        radius: parseFloat(document.getElementById('explorarRutas:radio').value) * 1000
    });
    PF('gmap-radio').addOverlay(circulo);
}

function getVars() {
    document.getElementById("explorarRutas:lng").value = latlng.lng();
    document.getElementById("explorarRutas:lat").value = latlng.lat();
}