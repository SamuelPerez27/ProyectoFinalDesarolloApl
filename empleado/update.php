<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}
$cedula = $_POST['cedula'];
$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];

$query ="UPDATE empleado SET cedula ='$cedula', nombre ='$nombre', apellido ='$apellido' WHERE id ='$id'";
$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos actualizados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>