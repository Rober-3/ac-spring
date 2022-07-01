function init() {
	console.log('Documento cargado y listo.');
}


/**
 * Función asociada al evento keyenter para el id:input#nombre. Llama mediante AJAX a
 * un servicio rest para comprobar si existe un nombre de usuario en la base de datos.   
 */
// Función que hace una llamada AJAX para comprobar que un nombre de usuario está disponible en la bbdd.
function buscarUsuario(event) {
	
	// console.debug(event);
	
	const nombre = event.target.value;
	console.debug(`Valor del input: ${nombre}`);
	
	const url = `http://localhost:8080/automoviles-clasicos/api/usuario?nombre=${nombre}`;
	
	// Llamada AJAX.
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", url );   
	xhttp.send();
	xhttp.onreadystatechange = function() { 
	
		let elNombreHelp = document.getElementById('nombreHelp');
		
		if (this.readyState == 4 && this.status == 200) {
			elNombreHelp.innerHTML = 'Nombre de usuario no disponible';
			elNombreHelp.classList.add('text-danger');
			elNombreHelp.classList.remove('text-success');
			
		} else {
			elNombreHelp.innerHTML = 'Nombre de usuario disponible';
			elNombreHelp.classList.add('text-success');
			elNombreHelp.classList.remove('text-danger');
		}
	
	} // onreadystatechange
	
} // buscarUsuario


/**
 * Función para mostrar u ocultar la contraseña. Cambia
 * el tipo de un input para que sea 'text' o 'password'.
 * 
 * @param idElemnt parámetro a cambiar.
 */
function showHidePass(idElement) {
	let elInput = document.getElementById(idElement);	
	elInput.type = (elInput.type == 'password' ) ? 'text' : 'password';
}


// Script para confirmar la eliminación de un registro.
function confirmar(modelo) {
	
	// The confirm() method returns true if the user clicked "OK", and false otherwise.
	// modelo es el parámetro que se le pasa al script desde la vista ('${a.modelo}).
	if ( confirm('¿Estás seguro de querer eliminar ' + modelo + '?') ){
		console.debug('Continúa el evento por defecto del ancla.');
		
	}else {
		console.debug('Previene o detiene el evento del ancla.');
		event.preventDefault();
	}
}

$('.carousel').carousel()
