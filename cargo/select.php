<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['cargo'] =array();
$query ="SELECT * FROM cargo";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_cargo'] =$row['0'];
  $index['nombre'] =$row['1'];
  $index['salario'] =$row['2'];


  array_push($result['cargo'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>