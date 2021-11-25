<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

//$id_empresa = $_POST['id_empresa'];
$nombre = $_POST['nombre'];
$id_empresa = $_POST['id_empresa'];
$id_metodo_pago = $_POST['id_metodo_pago'];
$valor = $_POST['valor'];
$concepto = $_POST['concepto'];
$fecha = $_POST['fecha'];
$id_cliente = $_POST['id_cliente'];
$id_tipo_cuenta = $_POST['id_tipo_cuenta'];


$query ="INSERT INTO cuentas(nombre,id_empresa,id_metodo_pago,valor,concepto,fecha,id_cliente,id_tipo_cuenta) values ('$nombre','$id_empresa', '$id_metodo_pago', '$valor', '$concepto','$fecha', '$id_cliente', '$id_tipo_cuenta')";
$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos insertados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>