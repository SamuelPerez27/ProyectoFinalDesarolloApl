<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['datos'] =array();
$query ="SELECT * FROM cuentas";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_cuenta'] =$row['0'];
  $index['nombre'] =$row['1'];
  $index['id_empresa'] =$row['2'];
  $index['id_metodo_pago'] =$row['3'];
  $index['valor'] =$row['4'];
  $index['concepto'] =$row['5'];
  $index['fecha'] =$row['6'];
  $index['id_cliente'] =$row['7'];
  $index['id_tipo_cuenta'] =$row['8'];


  array_push($result['datos'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>