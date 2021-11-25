<?php

$conexion =mysqli_connect("localhost", "id17880816_lpilier", "iCA(CjJp)rrRKXB2","id17880816_bdcrud");
if(!$conexion){
    echo "Error de conexion";
}
$cedula = $_POST['cedula'];
$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];

$query ="UPDATE emepleado SET cedula ='$cedula', nombre ='$nombre', apellido ='$apellido' WHERE id ='$id'";
$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos actualizados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>