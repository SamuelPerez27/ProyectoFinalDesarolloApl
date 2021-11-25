<?php
$conexion =mysqli_connect("localhost", "id17880816_lpilier", "iCA(CjJp)rrRKXB2","id17880816_bdcrud");
if(!$conexion){
    echo "Error de conexion";
}

$id =$_POST["id"];
$query ="DELETE FROM empleado WHERE id='$id'";
$result=mysqli_query($conexion,$query);
if($result){
 echo"datos eliminados correctamente";
}else{
    echo"datos eliminados correctamente";
}

mysqli_close($conexion);
?>