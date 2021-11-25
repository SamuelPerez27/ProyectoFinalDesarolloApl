<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['datos'] =array();
$id_empresa = $_POST['id_empresa'];
$query ="SELECT * FROM empresas WHERE id_empresa = '$id_empresa'";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_empresa'] =$row['0'];
  $index['nombre'] =$row['1'];
  $index['descripcion'] =$row['2'];
  $index['propietario'] =$row['3'];
  $index['usuario'] =$row['4'];
  $index['contrasena'] =$row['5'];
  $index['id_estado'] =$row['5'];
  
  array_push($result['datos'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>