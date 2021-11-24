<?php

$conexion =mysqli_connect("localhost", "id17880816_lpilier", "iCA(CjJp)rrRKXB2","id17880816_bdcrud");
if(!$conexion){
    echo "Error de conexion";
}

//$id_empresa = $_POST['id_empresa'];
$nombre = $_POST['nombre'];
$descripcion = $_POST['descripcion'];
$propietario = $_POST['propietario'];
$usuario = $_POST['usuario'];
$contrasena = $_POST['contrasena'];

$query ="INSERT INTO empleado(nombre,descripcion,propietario,usuario,contrasena) values ('$nombre','$descripcion', '$propietario', '$usuario', '$contrasena')";
$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos insertados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>