<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$usuario= $_POST['usuario'];
$contrasena= $_POST['contrasena'];
$nombre= $_POST['nombre'];

$query ="SELECT * FROM empresas WHERE usuario='$usuario' AND contrasena='$contrasena' AND nombre='$nombre'";
$response = mysqli_query($conexion, $query);

if($response->num_rows > 0){
    echo "Usuario o contraseña o empresa correctos";
}else{
    echo "Usuario o contraseña o empresa incorrectos";
}

?>