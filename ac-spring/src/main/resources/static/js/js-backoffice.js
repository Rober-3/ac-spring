function init() {
	console.log('Documento cargado y listo.');	
}


//Script para confirmar la eliminación de un clásico o una marca.
function confirmar(parametro) {

	// The confirm() method returns true if the user clicked "OK", and false otherwise.
	if ( confirm('¿Estás seguro de querer eliminar ' + parametro + '?') ){

		console.debug('Continúa el evento por defecto del ancla.');

	}else {

		console.debug('Previene o detiene el evento del ancla.');
		event.preventDefault();
	}
}


//Script de Bootstrap para desplegar la barra lateral.
(function($) {
	"use strict";

	// Add active state to sidbar nav links
	var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
	$("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
		if (this.href === path) {
			$(this).addClass("active");
		}
	});

	// Toggle the side navigation
	$("#sidebarToggle").on("click", function(e) {
		e.preventDefault();
		$("body").toggleClass("sb-sidenav-toggled");
	});
})(jQuery);
