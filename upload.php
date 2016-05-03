<?php
include 'connect.php';
$sql = 'INSERT INTO Leaderboard VALUES ($_POST["score"], $_POST["id"])';
$rs = $conn->query($sql);
?>
