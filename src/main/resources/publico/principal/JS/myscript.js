
$(document).ready(main);
var contador = 1;

function main () {
	$('.menu_bar').click(function(){
		if (contador == 1) {
			$('nav').animate({
				left: '0'
			});
			contador = 0;
		} else {
			contador = 1;
			$('nav').animate({
				left: '-100%'
			});
		}
	});

	// Mostramos y ocultamos submenus
	$('.submenu').click(function(){
		$(this).children('.children').slideToggle();
	});
}
$(document).ready(function(){

	// Activate tooltip
	$('[data-toggle="tooltip"]').tooltip();
	
	// Select/Deselect checkboxes
	var checkbox = $('table tbody input[type="checkbox"]');
	$("#selectAll").click(function(){
		if(this.checked){
			checkbox.each(function(){
				this.checked = true;                        
			});
		} else{
			checkbox.each(function(){
				this.checked = false;                        
			});
		} 
	});
	checkbox.click(function(){
		if(!this.checked){
			$("#selectAll").prop("checked", false);
		}
	});
});
$(document).ready(function($){
	console.log("Entrado a script")
	var array = [];
	var count_click = 0;
	$('#table tbody').on('click','#ok',function(){
		count_click_add();
		//	console.log("Entrando a funcion");
		var curRow = $(this).closest('tr');
		//	var contenido =$('table tbody td');
		//	console.log(contenido.text);
		//	console.log(contenido);
		//   var col1 = curRow.find('td:eq(0)').text();F
		var col1 = curRow.find('td:eq(1)').text();
		var col2 = curRow.find('td:eq(2)').text();
		//console.log(curRow);
		//console.log(col2, col1);
		array.push(col1)
		array.push(col2)
		console.log("Con array");
		//alert(array)
		//alert(array);
		//alert($("tr.selected td:eq(1)" ).html());
		//document.getElementsByName("test").value = col1;
		//document.getElementById('cont1').innerHTML=col1;
		//	document.getElementById('carrito').innerHTML='Carrito: '+count_click;
		var modal =''
		//modal += '<form method="post" action="/update" >'
		//modal+='Carrito: '+count_click
		modal+= '<input type="hidden"  id="defaultForm-email" class="form-control validate" value="'+col1+'" name="tel">'
		//modal += '</form>'
		$(document).find('.btn btn-default"').html(modal);


	});
	function count_click_add() {
		count_click += 1;
	}
});