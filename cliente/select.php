<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['cliente'] =array();
$query ="SELECT * FROM cliente";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_cliente']=$row['0'];
  $index['cedula'] =$row['1'];
  $index['nombre'] =$row['2'];
  $index['apellido'] =$row['3'];
  $index['id_empresa'] =$row['4'];


  array_push($result['cliente'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>