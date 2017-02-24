<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<form action="r" method="get">
<div class="jumbotron">
	<h1>Reserva Venta!</h1>
</div>	
<div class="container">
<div class="form-group">
<p><input class="form-control" type="text" name="nombre" placeholder="Introduce tu nombre"> </p>
<p><input class="form-control" type="tel" name="telefono" placeholder="Teléfono"></p>
<!-- <p><input class="form-control" type="email" name="mail" placeholder="Email"></p> -->
<p><input class="form-control" type="date" name="fecha"></p>
<p><select class="form-control" name="hora">
  <option value="13">13:00</option>
  <option value="14">14:00</option>
</select></p>
<p><input class="form-control" type="number" name="comensales" placeholder="Número de comensales"></p>

<p><input type="submit" class="btn-primary" name="submit"></p>
</div>
</div>


</form>
</body>
</html>