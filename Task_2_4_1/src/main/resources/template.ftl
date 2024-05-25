<!DOCTYPE html>
<html lang='en'>
<head>
    <title>Task Progress Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f9;
        }
        h2, h3 {
            color: #333;
        }
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .table thead th {
            padding: 10px;
            font-weight: 600;
            font-size: 16px;
            line-height: 20px;
            text-align: left;
            color: #fff;
            background-color: #444441;
        }
        .table tbody td {
            padding: 10px;
            font-size: 14px;
            line-height: 20px;
            color: #444441;
            border-top: 1px solid #ccc;
        }
        .table tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .table tbody tr:hover {
            background-color: #f1f1f1;
        }
        .table tbody td {
            text-align: left;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<h2>${group.id}</h2>
<#list tasks as task>
    <h3>${task.id}</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Student</th>
            <th>Build</th>
            <th>Documentation</th>
            <th>Tests Passed</th>
            <th>Tests Ignored</th>
            <th>Soft Deadline</th>
            <th>Hard Deadline</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <#list task.progress as p>
            <tr>
                <td>${p.student!'None'}</td>
                <#if p.build>
                    <td>Successful</td>
                <#else>
                    <td>Failed</td>
                </#if>
                <#if p.docGenerated>
                    <td>Generated</td>
                <#else>
                    <td>Missing</td>
                </#if>
                <#if p.testsCount??>
                    <td>${p.testsPassed}/${p.testsCount}</td>
                <#else>
                    <td>Error</td>
                </#if>
                <#if p.testsIgnored??>
                    <td>${p.testsIgnored}</td>
                <#else>
                    <td>0</td>
                </#if>
                <#if p.softDeadline>
                    <td>OK</td>
                <#else>
                    <td>Missed</td>
                </#if>
                <#if p.hardDeadline>
                    <td>OK</td>
                <#else>
                    <td>Missed</td>
                </#if>
                <td>${p.score!'None'}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</#list>
</body>
</html>