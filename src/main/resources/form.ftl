<html>
<head>
    <title>Test form</title>
</head>
<body>
    <form action="/select_action" method="post">
        <p>Select one item</p>
        <#list items as item>
            <p>
                <input type="radio" name="item" value="${item}">
                    ${item}
                </input>
            </p>
        </#list>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>