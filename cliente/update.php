<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}
$cedula = $_POST['cedula'];
$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];
$id_empresa = $_POST['id_empresa'];

$query ="UPDATE cliente SET cedula ='$cedula', nombre ='$nombre', apellido =$apellido, id_empresa=$id_empresa WHERE id =$id";
$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos actualizados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>