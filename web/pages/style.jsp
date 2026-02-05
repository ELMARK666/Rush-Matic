<style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            min-height: 100vh;  
            background-color: white;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0px 10px;
            background: linear-gradient(to right, #dc3545 80%, #ffffff 90%);
        }
        .header a {
            color: white !important;
            text-decoration: none;
            font-size: 15px;
            text-transform: uppercase;
        }
        .header a:hover {
            color: white !important;
            text-decoration: none;
        }
        .header a i {
            color: white !important;
        }
        .header, .sub-header, .sub-sub-header {
            position: relative;
        }
        .sub-header {
            background-color: #7a7a7380;
            color: black;
            padding: 10px 40px;
        }
        .sub-sub-header {
            background-color: #7a7a733d;
            color: black;
            padding: 10px 40px;
            display: flex;
            gap: 50px;
            justify-content: space-between;
        }
        .submenu {
            display: flex;
            gap: 40px; 
            align-items: center;
            flex-wrap: wrap; 
        }
        .submenu a, .subsubmenu a {
            color: inherit;   
            font-weight: normal;
            transition: color 0.2s, font-weight 0.2s;
            margin-right: 20px;
        }
        .header a:hover, .submenu a:hover, .subsubmenu a:hover {
            text-decoration: none;  
        }
        .dropdown {
            position: relative;
        }
        .form-popup {
            display: none;
            position: absolute; 
            top: 120px; 
            left: 50%;
            transform: translateX(-50%);
            background: #fff;
            color: #333;
            padding: 10px;
            box-shadow: 0 4px 5px rgba(0,0,0,0.3);
            width: 100%;  
            max-width: 100%;
            z-index: 9999;
        }
        .form-popup form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        .form-popup input{
            padding: 2px;
            font-size: 14px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .form-popup select{
            padding: 5px;
            font-size: 14px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .form-popup button {
            padding: 10px;
            font-size: 14px;
            border-radius: 4px;
            border: 1px solid #ccc;
            background-color: #ED3500;
            color: white;
            border: none;
            cursor: pointer;
        }
        .form-popup button:hover {
            background-color: #c72d00;
        }
        .form-row {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-left: 50px; 
        }
        .inline-form {
            display: flex;
            flex-wrap: wrap;   
            align-items: center;
            gap: 10px;         
        }
        .inline-form label {
            margin: 0 5px 0 0; 
        }
        .inline-form input, .inline-form select{
            padding: 5px 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .inline-form button {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        .inline-form button:hover {
            background-color: #45a049;
        }
        .active-menu { 
            color: #8b0000 !important;
            border-radius: 4px;
        }
        .dropdown {
            position: relative;
            display: inline-block;
        }
        footer {
            background-color: black;
            color: white;
            text-align: center;
            padding: 10px;
            margin-top: auto; 
        }
        footer p {
            font-size: 0.9em;
        }
        footer a {
            color: #e60000;
            text-decoration: none;
        }
        footer a:hover {
            text-decoration: underline;
        }
        .tables-container.rent {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            margin-top: 20px;
            gap: 20px;
            width: 100%;
        }
        .tables-container.rent > div {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            padding: 15px;
            width: 90%; 
            max-width: 1200px; 
            overflow: auto;
            height: 66vh;
            color: #000;
        }
        .tables-container.rent table thead {
            position: sticky;
            top: 0;
            background-color: #f9f9f9;
            z-index: 10;
        }

        .tables-container.rent table th,
        .tables-container.rent table td {
            text-align: center;
            vertical-align: middle;
            white-space: nowrap;
        }
        .dataTables_wrapper {
            width: 100%;
            overflow-x: auto;
        }
        table.dataTable {
            width: 100% !important;
            table-layout: auto;
            white-space: nowrap;
        }
        /*ASSETREGISTRATION*/
        .form-popup {
            display: flex;
            flex-direction: column;
            gap: 9px;
            font-size: 14px;
          }
          /* Common row container */
        .form-row1 {
            display: grid;
            grid-template-columns: 110px repeat(9, 1fr);
            column-gap: 4px;  /* less space between input fields */
            row-gap: 6px;    /* slightly more between rows for readability */
        }

          /* Label column */
        .form-row1 b {
             grid-column: 1 / 2;
            white-space: nowrap;
        }

          /* Make inputs uniform */
        .form-control {
            height: 32px;
            padding: 4px 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 13px;
        }

           /* Specific column width adjustments */
        #generate_date,#region_gen,#zone_gen,#zone_asset, #region_asset, #asset_cat, #dep_date, #retire_date, #ref_id, #date_rec,#submit {
           width: 180px;
           padding-left:2px;
        }

        #branch_id,#submit {
            width: 180px;
        }

        #aqui_cost, #asset_code, #life, #scrap_value {
           width: 180px;
        }
        #submit:hover{
            background-color: #c72d00;
            color:white;
            border-color:#f2f2f2;
        }
        #submit:active{
            background-color: #000000;
            color:white;

        }
        .glecontainer {
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .gleheader {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 20px;
        }
        .form-section {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr; /* 3 columns */
            gap: 15px;
            align-items: center;
            margin-bottom: 20px;
        }

        .input-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        .input-group.single-col {
            grid-column: span 3;
        }
        .input-group.buttons {
            display: flex;
            gap: 10px;
        }
        label {
            font-size: 14px;
            color: #555;
        }
        input, select {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
            width: 100%;
        }
        .input-with-button {
            display: flex;
            align-items: center; 
            gap: 5px;
        }
        .input-group input[type="text"] {
            flex: 1;
        }
        .search-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 4px;
            font-weight: bold;
        }
        .action-button {
            padding: 10px;
            font-size: 14px;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
            width: 100%;
        }
        .action-button:hover, .search-button:hover {
            transform: scale(1.03); 
        }
        .display-button {
            background-color: #28a745;
            color: white;
        }
        .export-button {
            background-color: #17a2b8;
            color: white;
        }
        .clear-button {
            background-color: red;
            color: white;
        }
        .table-section {
            margin-top: 30px;
        }
        .table-header {
            display: flex;
            background-color: #dc3545;
            color: white;
            font-weight: bold;
            padding: 10px;
            border-radius: 4px;
        }
        .table-header > div {
            flex: 1;
            text-align: center;
        }
        .table-header .col-date { flex: 1; }
        .table-header .col-zone { flex: 1; }
        .table-header .col-region { flex: 1; }
        .table-header .col-branch { flex: 1.5; }
        .table-header .col-gl-code { flex: 1; }
        .table-header .col-amount { flex: 1; }
        .table-header .col-category { flex: 1; }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #777;
            border: 1px solid #ddd;
            border-top: none;
            border-radius: 0 0 4px 4px;
        }
        .input-group.buttons button {
            flex: 1;
        }
        .modal {
            display: none; /* hidden by default */
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);

            /* Flexbox for centering */
            display: flex;
            justify-content: center; /* horizontal center */
            align-items: center;     /* vertical center */
            z-index: 1000;
        }
        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            width: 550px;
            max-width: 90%;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            position: relative;
        }
        .modal .close {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 20px;
            cursor: pointer;
        }
        .popup-container {
            position: absolute;
            top: 100%; 
            left: 0;
            right: 0;
            background-color: #fff;
/*            border-top: 2px solid #FB4141; */
            border-bottom: 1px solid #ddd;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 10px 20px;
            z-index: 1000;
        }

        .popup-container form,
        .popup-container .form-row {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }
        .registrationcontainer {
            display: none;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            justify-content: center;
            align-items: flex-start; 
            z-index: 1000;
        }

        .registrationcontainer .form-popup {
            background: #fff;
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            max-width: 1000px; 
            width:85%; 
            margin-top: 50px;
        }
        .incss{
            flex:1; 
            height:40px; 
            font-size:16px; 
            padding:10px 10px; 
            border:1px solid #ccc; 
            border-radius:5px;
        }
        .dashboard {
            display: flex;
            gap: 20px;
            justify-content: flex-start; 
            align-items: flex-start;
            max-width: 1400px;
            margin: 0; 
            padding-left: 0; 
        }
        #welcomeContainer {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 3px 6px rgba(0,0,0,0.1);
            padding: 25px 20px;
            font-family: Arial, sans-serif;
            text-align: left;
            height: 90vh;
            margin-left: 0; 
        }
        .container {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 3px 6px rgba(0,0,0,0.1);
            padding: 20px;
        }
        #welcomeContainer{
            flex: none;              
            width: 300px;            
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 3px 6px rgba(0,0,0,0.1);
            padding: 25px 20px;
            font-family: Arial, sans-serif;
            text-align: left;
            height: 90vh;
            margin-left: 0;
        }
        #welcomeContainerRight {
            flex: none;              
            width: 400px;            
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 3px 6px rgba(0,0,0,0.1);
            padding: 25px 20px;
            font-family: Arial, sans-serif;
            text-align: left;
            height: 90vh;
            margin-left: 0;
        }
        .charts {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .section h4 {
            margin-bottom: 8px;
            font-weight: bold;
            text-align: left;
        }
        .indent {
            margin-left: 25px;
        }
        canvas {
            max-width: 100%;
        }
        .active-link {
            color: #8B0000 !important;
            font-weight: bold;
        }
        .form-popup, .hotransactioncontainer > div > div > div[style*="margin-top"] {
            transition: all 0.3s ease;
        }
        .hotranimation {
            transition: all 0.3s ease;
        }
        .hotranimation .form-popup,
        .hotranimation .edi-logs {
            transition: all 0.4s ease;
        }
        .hotranimation .form-popup {
            opacity: 1;
            height: auto;
            overflow: hidden;
            margin-bottom: 20px;
        }
        .hotranimation.form-hidden .form-popup {
            opacity: 0;
            height: 0;
            margin: 0;
            padding: 0;
        }
        .hotranimation .edi-logs {
            margin-top: 20px;
        }
        .hotranimation.form-hidden .edi-logs {
            margin-top: -60px;
        }
        .is-invalid {
            border: 1px solid #dc3545 !important;
            background-color: #fff5f5;
        }
        .modals-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            max-width: 100vw;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            position: relative;
            max-height: 90vh;
        }
        #ediLogs {
            transition: margin-top 0.3s ease, opacity 0.3s ease;
        }
        .popup-hidden {
            display: none !important;
        }
        .edi-container {
            display: flex;
            justify-content: center;
            gap: 20px;
            width: 100%;
        }
        .edi-table-wrapper {
            flex: none;
        }
        .edi-table thead {
            position: sticky;
            top: 0;
            background: white;
            z-index: 1;
        }
        .edi-table-wrapper.table1 {
            width: 15%;
            margin-left: 20px;
        }
        .edi-table-wrapper.table2 {
            width: 35%;
        }
        .edi-table-wrapper.table3 {
            width: 47%;
            margin-right: 20px;
        }
        #livesearch {
            position: absolute;      /* Stay on top of content */
            background-color: white;
            width: 220px;            /* Match input width */
            border: 1px solid #A5ACB2;
            max-height: 350px;       /* Scroll if more than this */
            overflow-y: auto;        /* Vertical scroll */
            z-index: 1000;           /* Ensure it's above other elements */
            display: none;           /* Hide by default */
        }

        /* Each result item */
        #livesearch li {
            padding: 8px;
            border-bottom: 1px solid #ddd;
            cursor: pointer;
        }

        #livesearch li:hover {
            background-color: #f0f0f0;
        }
    </style> 