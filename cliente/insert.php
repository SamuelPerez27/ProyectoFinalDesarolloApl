<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$cedula = $_POST['cedula'];
$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];


$query ="INSERT INTO datos(cedula,nombre,apellido) values ('$cedula','$nombre', '$apellido')";
$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos insertados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>