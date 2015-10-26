<?php 


require("config.php");
//connect to database:
$connection = mysqli_connect($dbhost, $dbuname, $dbpass, $dbname);
if(!$connection){
	echo "Error: Unable to connect to MySQL." . PHP_EOL;
	echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
	echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
	exit;
}

//run the select:
$result = mysqli_query($connection,  "SELECT catID, catName, parentID FROM categories ORDER BY catOrd");
if(!$result){
	echo "Error: Unable to query categories to MySQL." . PHP_EOL;
	echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
	echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
	exit;
}

$rows = array();
//loop the result set:
while ($row = mysqli_fetch_assoc($result)){   
	$rows[ $row[0] ][] = $row;	
}
mysqli_free_result($result);
mysqli_close($connection);

// format at JSON:
$jsonString = json_encode($rows);
// change the root element  to 
// it is starting with {"":[{"catID":" 
// replace it with {"categories":[{"catID":" 
// couldn't find any faster method...
$newstr = '{"categories'. substr($jsonString , 2) ;

print $newstr;




?>