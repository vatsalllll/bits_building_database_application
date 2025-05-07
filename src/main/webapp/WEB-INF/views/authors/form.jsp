<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${author.id == null ? 'Add New Author' : 'Edit Author'}</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <div class="container">
        <h1>${author.id == null ? 'Add New Author' : 'Edit Author'}</h1>
        
        <form action="${author.id == null ? '/authors' : '/authors/'.concat(author.id)}" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${author.name}" required />
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save</button>
                <a href="/authors" class="btn">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html> 