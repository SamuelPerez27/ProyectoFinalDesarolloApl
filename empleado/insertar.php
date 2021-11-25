<?php

$conexion =mysqli_connect("localhost", "id17880816_lpilier", "iCA(CjJp)rrRKXB2","id17880816_bdcrud");
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