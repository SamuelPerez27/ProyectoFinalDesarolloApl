<?php
$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
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