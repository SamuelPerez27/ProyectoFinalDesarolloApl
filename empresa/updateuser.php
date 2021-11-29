<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$id_empresa = $_POST['id_empresa'];
//$nombre = $_POST['nombre'];
//$descripcion = $_POST['descripcion'];
//$propietario = $_POST['propietario'];
$usuario = $_POST['usuario'];
$contrasena = $_POST['contrasena'];

$query ="UPDATE empresas SET usuario = '$usuario', contrasena = '$contrasena' 
                                                   WHERE id_empresa = $id_empresa";

$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos insertados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>