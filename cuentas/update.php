<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}


$id_cuenta = $_POST['id_cuenta'];
$nombre = $_POST['nombre'];
$id_empresa = $_POST['id_empresa'];
$id_metodo_pago = $_POST['id_metodo_pago'];
$valor = $_POST['valor'];
$concepto = $_POST['concepto'];
$fecha = $_POST['fecha'];
$id_cliente = $_POST['id_cliente'];
$id_tipo_cuenta = $_POST['id_tipo_cuenta'];


$query ="UPDATE cuentas SET nombre = '$nombre', id_empresa = $id_empresa, id_metodo_pago = $id_metodo_pago, valor = $valor,
                           concepto = '$concepto',  fecha = $fecha, id_cliente = $id_cliente, id_tipo_cuenta = $id_tipo_cuenta
                                                   WHERE id_cuenta = $id_cuenta";

$resultado =mysqli_query($conexion,$query);

if($resultado){
echo "datos insertados";
}else{
echo "datos error";
}
mysqli_close($conexion);
?>