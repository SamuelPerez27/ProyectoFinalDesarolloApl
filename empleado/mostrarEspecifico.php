<?php

$conexion =mysqli_connect("localhost", "id17880816_lpilier", "iCA(CjJp)rrRKXB2","id17880816_bdcrud");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['empleado'] =array();
$query ="SELECT * FROM empleado WHERE cedula='$cedula'";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['cedula'] =$row['0'];
  $index['nombre'] =$row['1'];
  $index['apellido'] =$row['2'];


  array_push($result['empleado'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>