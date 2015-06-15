<?php
$connection = mysqli_connect('sql302.byethost12.com','b12_15941792','teammacro3');
if($connection)
{
          echo "Your Are connected";
}
else
{
     die("No connection is Established");
}



if(mysqli_select_db($connection,  'b12_15941792_caldb') == false)
{
       die("Db connection failed");
}
else
{
        echo "You are connected";
}

$field = $_POST['field'];
$sql=$connection->query("insert into Test (testfield) values('{$field}')");

if($sql == false)
{
     die("Select failed");
}

while($row=mysqli_fetch_assoc($sql))
$output[]=$row;
print(json_encode($output));
mysqli_close();
?>	