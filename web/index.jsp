<%--
  Created by IntelliJ IDEA.
  User: rahul
  Date: 25/12/2015
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Test Hotel Booking</title>
  </head>
  <body>
  <form name="Booking" method="post" action="/submit">
    <table width="450px">

      <tr>
        <td valign="top">
          <label for="fname">Full Name </label>
        </td>
        <td valign="top">
          <input  type="text" name="fname" maxlength="50" size="30">
        </td>
      </tr>

      <tr>
        <td valign="top">
          <label for="email">Email Address </label>
        </td>
        <td valign="top">
          <input  type="text" name="email" maxlength="80" size="30">
        </td>
      </tr>
      <tr>
        <td valign="top">
          <label for="room">Room Number</label>
        </td>
        <td valign="top">
          <select name="room">
            <option value="100">100</option>
            <option value="101">101</option>
            <option value="200">200</option>
            <option value="201">201</option>
          </select>
        </td>
      </tr>

      <tr>
        <td colspan="2" style="text-align:center">
          <input type="submit" value="Submit">
        </td>
      </tr>

      </table>
    </form>
  </body>
</html>
