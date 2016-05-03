<?php
include 'connect.php';
$sql = 'INSERT INTO Leaderboard (score, id) VALUES ($_POST["score"], $_POST["id"])';
$rs = $conn->query($sql);
?>
