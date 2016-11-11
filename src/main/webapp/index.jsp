<%@ page import="java.util.List" %>
<%@ page import="twitter4j.Status" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <spring:url value="/test.txt" htmlEscape="true" var="springUrl"/>

    <script type="text/javascript">
        function filter(arg) {

            window.location = "/filterTweets?filterCriteria=" + arg;
        }

        function updateSearchTerm() {
            var search = document.getElementById("searchQuery").value;
            window.location = "/updateSearchTerm?searchQuery=" + search;

        }
        var data = null;

        function refresh() {
            var search = document.getElementById("searchQuery").value;
            window.location = "/";
        }
    </script>
</head>
<body>
<h1 align="center">Twitter Feeds</h1>
<div align="center">
    <input type="text" name="twitterQuery" id="searchQuery">
    <button onclick="updateSearchTerm()">Search</button>
</div>
<div align="center">
    <input type="submit" value="Refresh" onclick="refresh()" align="right"/>
</div>
<div align="center">
    <Strong>Filter By</Strong>
    <input type="radio" name="filter" value="SEARCH_BY_HASH_TAG"
           onchange="filter('SEARCH_BY_HASH_TAG')">#tag</input>
    <input type="radio" name="filter" value="SEARCH_BY_EMBEDED_WORD" onchange="filter('SEARCH_BY_EMBEDED_WORD')">Embedded</input>
    <input type="radio" name="filter" value="SEARCH_BY_USER_NAME"
           onchange="filter('SEARCH_BY_USER_NAME')">UserName</input>
    <input type="radio" name="filter" value="SEARCH_BY_TWEET_TEXT" onchange="filter('SEARCH_BY_TWEET_TEXT')">Tweet
    Text</input>
</div>

<div align="center">

    <span>
    Current Search term : ${queryTerm}
    </span>

    <table id="tweetData" border="1px" width="50%"
           style="text-align: center; border-collapse: collapse; opacity: 0.98;">
        <tr>
            <th>User Account</th>
            <th>Tweet</th>
            <th>Date</th>
        </tr>
        <c:forEach items="${data}" var="tweets">
            <tr>
                <td>${tweets.user.name}</td>
                <td>${tweets.text}</td>
                <td>${tweets.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
    <%
        Object data = request.getAttribute("data");
        Gson gson = new Gson();
        System.out.println(gson.toJson(data));
    %>

</div>
</body>
</html>