<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['datos'] =array();
$query ="SELECT * FROM tipo_cuentas";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_cuenta'] =$row['0'];
  $index['nombre'] =$row['1'];

  array_push($result['datos'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>