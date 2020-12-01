
<html>
<head>
<link rel="stylesheet" href="query.css">

</head>


<body>

<h1>Enter your query</h1>


<form action="query.php" method="post">

    query: <input type="text" name="query"><br>
    <input name="submit" type="submit" >
</form>
<br><br>

</body>
</html>

</body>

<?php
if (isset($_POST["submit"]))
{

    $text = $_POST["query"];
    $myfile = fopen("text.txt", "w");
    fwrite($myfile, $text);
    fclose($myfile);
    $command = 'java Main';
    system($command);  
}
?>

