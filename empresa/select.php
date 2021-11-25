<?php

$conexion =mysqli_connect("localhost", "id17981054_root", "iCA(CjJp)rrRKXB2","id17880816_bdcrud");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['datos'] =array();
$query ="SELECT * FROM empleado";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_empresa'] =$row['0'];
  $index['nombre'] =$row['1'];
  $index['descripcion'] =$row['2'];
  $index['propietario'] =$row['3'];
  $index['usuario'] =$row['4'];
  $index['contrasena'] =$row['5'];

  array_push($result['datos'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>