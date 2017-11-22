<!DOCTYPE html>
<html>
<head>
<title>Fruit Shop</title>
</head>
<body>
  <?php
      // data from user
      $username  = isset($_POST['username']) ? $_POST['username'] : "";
      $antallApples  = isset($_POST['antallApples']) ? $_POST['antallApples'] : 0;
      $antallOranges = isset($_POST['antallOranges']) ? $_POST['antallOranges'] : 0;
      $antallBananas = isset($_POST['antallBananas']) ? $_POST['antallBananas'] : 0;
      $paymentOption = isset($_POST['paymentOption']) ? $_POST['paymentOption'] : "";

      // variables
      $ordersApples = 0;
      $ordersOranges = 0;
      $ordersBananas = 0;
      $priceApple = 0.69;
      $priceOrange = 0.59;
      $priceBanan = 0.39;

      // open file with orderdata
      $file = "orders.txt";
      $scanner = fopen($file, "c+");
      $data = "";

      // read file, and save data in variables
      if (file_exists($file)) {
        $data = fread($scanner, filesize($file) + 1);
        preg_match("/Apples: (\d+)/", $data, $fileApples);
        preg_match("/Oranges: (\d+)/", $data, $fileOranges);
        preg_match("/Bananas: (\d+)/", $data, $fileBananas);

        $ordersApples = intval($fileApples[1]);
        $ordersOranges = intval($fileOranges[1]);
        $ordersBananas = intval($fileBananas[1]);
      }

      // add userdata to orderfile
      $ordersApples += $antallApples;
      $ordersOranges += $antallOranges;
      $ordersBananas += $antallBananas;

      // write to file
      fseek($scanner, 0);
      fwrite($scanner, "Total number of apples: $ordersApples\n" + "Total number of oranges: $ordersOranges\n" + "Total number of bananas: $ordersBananas\n");
      fclose($scanner);
    ?>
  <h1>Receipt</h1>
  <table>
    <tr>
      <td> Name: <?php echo $username; ?> </td>
    </tr>
    <tr>
      <td> Apples: <?php echo $antallApples; ?> </td>
    </tr>
    <tr>
      <td> Oranges: <?php echo $antallOranges; ?> </td>
    </tr>
    <tr>
      <td> Bananas: <?php echo $antallBananas; ?> </td>
    </tr>
    <tr>
      <td> Total price: <?php echo $antallApples * $priceApple + $antallOranges * $priceOrange + $antallBananas * $antallBananas; ?> </td>
    </tr>
    <tr>
      <td> Payment selected: <?php echo $paymentOption; ?> </td>
    </tr>
  </table>
</body>
</html>
