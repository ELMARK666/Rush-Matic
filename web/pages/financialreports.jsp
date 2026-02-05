<div class="bsgeneralbalancesheetcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="bsgeneralbalancesheet_group" id="bsgeneralbalancesheet_group" data-prefix="bsgeneralbalancesheet" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="bsgeneralbalancesheet_zones" name="bsgeneralbalancesheet_zones" data-prefix="bsgeneralbalancesheet" required></select>
                        <select class="form-control region-select" id="bsgeneralbalancesheet_region" name="bsgeneralbalancesheet_region" data-prefix="bsgeneralbalancesheet" required></select>
                        <select class="form-control branch-select" id="bsgeneralbalancesheet_branch" name="bsgeneralbalancesheet_branch" data-prefix="bsgeneralbalancesheet" required></select>
                        <select class="form-control date_start" id="bsgeneralbalancesheet_month1" name="bsgeneralbalancesheet_month1" required></select>
                        <select class="form-control year_select" id="bsgeneralbalancesheet_year" name="bsgeneralbalancesheet_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <button type="button" class="form-control" id="bsgeneralbalancesheet_submit" name="bsgeneralbalancesheet_submit" data-prefix="bsgeneralbalancesheet">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="4">Region/Branch :</th> 
                            </tr>
                            <tr>
                                <th>Category</th> 
                                <th>GLCode</th> 
                                <th style="text-align-last: center;">Description</th>
                                <th style="text-align-last: center;">Amount</th> 
                            </tr>
                        </thead>
                        <tbody class="bsgeneralbalancesheet_report">
                            <tr>
                                <td colspan='5'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="horizontalbalancesheetcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="horizontalbalancesheet_group" id="horizontalbalancesheet_group" data-prefix="horizontalbalancesheet" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="horizontalbalancesheet_zones" name="horizontalbalancesheet_zones" data-prefix="horizontalbalancesheet" required></select>
                        <select class="form-control region-select" id="horizontalbalancesheet_region" name="horizontalbalancesheet_region" data-prefix="horizontalbalancesheet" required></select>
                        <select class="form-control branch-select" id="horizontalbalancesheet_branch" name="horizontalbalancesheet_branch" data-prefix="horizontalbalancesheet" required></select>
                        <select class="form-control date_start" name="horizontalbalancesheet_month1" required></select>
                        <select class="form-control date_end" name="horizontalbalancesheet_month2" required></select>
                        <select class="form-control year_select" name="horizontalbalancesheet_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <button type="button" class="form-control" id="horizontalbalancesheet_submit" name="horizontalbalancesheet_submit" data-prefix="horizontalbalancesheet">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="3">Region/Branch :</th>  
                                <th class="month3" style="text-align-last: center;"> </th> 
                                <th class="month2" style="text-align-last: center;"> </th> 
                                <th class="month1" style="text-align-last: center;"> </th>
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th> 
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Category</th>
                                <th style="text-align: left;">GL Code</th>
                                <th style="text-align: center;">Description</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th id="3vs2" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                                <th id="3vs1" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                            </tr>
                        </thead>
                        <tbody class="horizontalbalancesheet_report">
                            <tr>
                                <td colspan='11'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="verticalbalancesheetcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="verticalbalancesheet_group" id="verticalbalancesheet_group" data-prefix="verticalbalancesheet" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="verticalbalancesheet_zones" name="verticalbalancesheet_zones" data-prefix="verticalbalancesheet" required></select>
                        <select class="form-control region-select" id="verticalbalancesheet_region" name="verticalbalancesheet_region" data-prefix="verticalbalancesheet" required></select>
                        <select class="form-control branch-select" id="verticalbalancesheet_branch" name="verticalbalancesheet_branch" data-prefix="verticalbalancesheet" required></select>
                        <select class="form-control date_start" name="verticalbalancesheet_month1" required></select>
                        <select class="form-control date_end" name="verticalbalancesheet_month2" required></select>
                        <select class="form-control year_select" name="verticalbalancesheet_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <button type="button" class="form-control" id="verticalbalancesheet_submit" name="verticalbalancesheet_submit" data-prefix="verticalbalancesheet">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="3">Region/Branch :</th>  
                                <th class="month3" colspan="2"style="text-align-last: center;"> </th> 
                                <th class="month2" colspan="2"style="text-align-last: center;"> </th> 
                                <th class="month1" colspan="2"style="text-align-last: center;"> </th>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Category</th>
                                <th style="text-align: left;">GL Code</th>
                                <th style="text-align: center;">Description</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;"> % </th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;"> % </th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;"> % </th>
                            </tr>
                        </thead>
                        <tbody class="verticalbalancesheet_report">
                            <tr>
                                <td colspan='11'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="bylevelbalancesheetcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="bylevelbalancesheet_group" id="bylevelbalancesheet_group" data-prefix="bylevelbalancesheet" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="bylevelbalancesheet_zones" name="bylevelbalancesheet_zones" data-prefix="bylevelbalancesheet" required></select>
                        <select class="form-control region-select" id="bylevelbalancesheet_region" name="bylevelbalancesheet_region" data-prefix="bylevelbalancesheet" required></select>
                        <select class="form-control branch-select" id="bylevelbalancesheet_branch" name="bylevelbalancesheet_branch" data-prefix="bylevelbalancesheet" required></select>
                        <select class="form-control date_start" name="bylevelbalancesheet_month1" required></select>
                        <select class="form-control date_end" name="bylevelbalancesheet_month2" required></select>
                        <select class="form-control year_select" name="bylevelbalancesheet_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <select class="form-control level-select" name="bylevelbalancesheet_level" id="bylevelbalancesheet_level" data-prefix="bylevelbalancesheet" required>
                            <option value="">-Level-</option>
                            <option value="1">Level 1</option>
                            <option value="2">Level 2</option>
                            <option value="3">Level 3</option>
                            <option value="4">Level 4</option>
                        </select><button type="button" class="form-control" id="bylevelbalancesheet_submit" name="bylevelbalancesheet_submit" data-prefix="bylevelbalancesheet">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="2">Region/Branch :</th>  
                                <th class="month3" style="text-align-last: center;"> </th> 
                                <th class="month2" style="text-align-last: center;"> </th> 
                                <th class="month1" style="text-align-last: center;"> </th>
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th> 
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th>
                            </tr>
                            <tr>
                                <th style="text-align: left;"></th>
                                <th style="text-align: left;">Category</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th class="3vs2" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                                <th class="3vs1" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                            </tr>
                        </thead>
                        <tbody class="bylevelbalancesheet_report">
                            <tr>
                                <td colspan='9'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="isgeneralincomestatementcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="isgeneralincomestatement_group" id="isgeneralincomestatement_group" data-prefix="isgeneralincomestatement" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Area">Area</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="isgeneralincomestatement_zones" name="isgeneralincomestatement_zones" data-prefix="isgeneralincomestatement" required></select>
                        <select class="form-control region-select" id="isgeneralincomestatement_region" name="isgeneralincomestatement_region" data-prefix="isgeneralincomestatement" required></select>
                        <select class="form-control branch-select" id="isgeneralincomestatement_branch" name="isgeneralincomestatement_branch" data-prefix="isgeneralincomestatement" required></select>
                        <select class="form-control date_start" id="isgeneralincomestatement_month1" name="isgeneralincomestatement_month1" required></select>
                        <select class="form-control year_select" id="isgeneralincomestatement_year" name="isgeneralincomestatement_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <button type="button" class="form-control" id="isgeneralincomestatement_submit" name="isgeneralincomestatement_submit" data-prefix="isgeneralincomestatement">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="4">Region/Branch :</th> 
                            </tr>
                            <tr>
                                <th>Category</th> 
                                <th>GLCode</th> 
                                <th style="text-align-last: center;">Description</th>
                                <th style="text-align-last: center;">Amount</th> 
                                <th style="text-align-last: center;">%</th> 
                            </tr>
                        </thead>
                        <tbody class="isgeneralincomestatement_report">
                            <tr>
                                <td colspan='5'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="horizontalincomestatementcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="horizontalincomestatement_group" id="horizontalincomestatement_group" data-prefix="horizontalincomestatement" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="horizontalincomestatement_zones" name="horizontalincomestatement_zones" data-prefix="horizontalincomestatement" required></select>
                        <select class="form-control region-select" id="horizontalincomestatement_region" name="horizontalincomestatement_region" data-prefix="horizontalincomestatement" required></select>
                        <select class="form-control branch-select" id="horizontalincomestatement_branch" name="horizontalincomestatement_branch" data-prefix="horizontalincomestatement" required></select>
                        <select class="form-control date_start" name="horizontalincomestatement_month1" required></select>
                        <select class="form-control date_end" name="horizontalincomestatement_month2" required></select>
                        <select class="form-control year_select" name="horizontalincomestatement_year" required>
                            <option value="">YYYY</option>
                        </select><button type="button" class="form-control" id="horizontalincomestatement_submit" name="horizontalincomestatement_submit" data-prefix="horizontalincomestatement">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel</button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="3">Region/Branch :</th>  
                                <th class="month3" style="text-align-last: center;"> </th> 
                                <th class="month2" style="text-align-last: center;"> </th> 
                                <th class="month1" style="text-align-last: center;"> </th>
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th> 
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Category</th>
                                <th style="text-align: left;">GL Code</th>
                                <th style="text-align: center;">Description</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th class="3vs2" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                                <th class="3vs1" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                            </tr>
                        </thead>
                        <tbody class="horizontalincomestatement_report">
                            <tr>
                                <td colspan='11'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="verticalincomestatementcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="verticalincomestatement_group" id="verticalincomestatement_group" data-prefix="verticalincomestatement" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="verticalincomestatement_zones" name="verticalincomestatement_zones" data-prefix="verticalincomestatement" required></select>
                        <select class="form-control region-select" id="verticalincomestatement_region" name="verticalincomestatement_region" data-prefix="verticalincomestatement" required></select>
                        <select class="form-control branch-select" id="verticalincomestatement_branch" name="verticalincomestatement_branch" data-prefix="verticalincomestatement" required></select>
                        <select class="form-control date_start" name="verticalincomestatement_month1" required></select>
                        <select class="form-control date_end" name="verticalincomestatement_month2" required></select>
                        <select class="form-control year_select" name="verticalincomestatement_year" required>
                            <option value="">YYYY</option>
                        </select><button type="button" class="form-control" id="verticalincomestatement_submit" name="verticalincomestatement_submit" data-prefix="verticalincomestatement">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel</button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="3">Region/Branch :</th>  
                                <th class="month3" colspan="2"style="text-align-last: center;"> </th> 
                                <th class="month2" colspan="2"style="text-align-last: center;"> </th> 
                                <th class="month1" colspan="2"style="text-align-last: center;"> </th>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Category</th>
                                <th style="text-align: left;">GL Code</th>
                                <th style="text-align: center;">Description</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;"> % </th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;"> % </th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;"> % </th>
                            </tr>
                        </thead>
                        <tbody class="verticalincomestatement_report">
                            <tr>
                                <td colspan='11'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="bylevelincomestatementcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="bylevelincomestatement_group" id="bylevelincomestatement_group" data-prefix="bylevelincomestatement" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="bylevelincomestatement_zones" name="bylevelincomestatement_zones" data-prefix="bylevelincomestatement" required></select>
                        <select class="form-control region-select" id="bylevelincomestatement_region" name="bylevelincomestatement_region" data-prefix="bylevelincomestatement" required></select>
                        <select class="form-control branch-select" id="bylevelincomestatement_branch" name="bylevelincomestatement_branch" data-prefix="bylevelincomestatement" required></select>
                        <select class="form-control date_start" name="bylevelincomestatement_month1" required></select>
                        <select class="form-control date_end" name="bylevelincomestatement_month2" required></select>
                        <select class="form-control year_select" name="bylevelincomestatement_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <select class="form-control level-select" name="bylevelincomestatement_level" id="bylevelincomestatement_level" data-prefix="bylevelincomestatement" required>
                            <option value="default">-Level-</option>
                            <option value="1">Level 1</option>
                            <option value="2">Level 2</option>
                            <option value="3">Level 3</option>
                            <option value="4">Level 4</option>
                        </select>
                        <button type="button" class="form-control" id="bylevelincomestatement_submit" name="bylevelincomestatement_submit" data-prefix="bylevelincomestatement">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th id="nameofregion" colspan="2">Region/Branch :</th>  
                                <th class="month3" style="text-align-last: center;"> </th> 
                                <th class="month2" style="text-align-last: center;"> </th> 
                                <th class="month1" style="text-align-last: center;"> </th>
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th> 
                                <th colspan="2" style="text-align-last: center;">INC / DEC</th>
                            </tr>
                            <tr>
                                <th style="text-align: left;"></th>
                                <th style="text-align: left;">Category</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th style="text-align: center;">Amount</th>
                                <th class="3vs2" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                                <th class="3vs1" style="text-align: center;">  </th>
                                <th style="text-align: center;"> % </th>
                            </tr>
                        </thead>
                        <tbody class="bylevelincomestatement_report">
                            <tr>
                                <td colspan='9'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="monthlybranchincomestatementcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" id="monthlybranchincomestatement_zones" name="monthlybranchincomestatement_zones" data-prefix="monthlybranchincomestatement" required></select>
                        <select class="form-control region-select" id="monthlybranchincomestatement_region" name="monthlybranchincomestatement_region" data-prefix="monthlybranchincomestatement" required></select>
                        <select class="form-control branch-select" id="monthlybranchincomestatement_branch" name="monthlybranchincomestatement_branch" data-prefix="monthlybranchincomestatement" required></select>
                        <select class="form-control level-select" id="levelSelect">
                           <option value="default">Default</option>
                           <option value="1">Level 1</option>
                           <option value="2">Level 2</option>
                           <option value="3">Level 3</option>
                           <option value="4">Level 4</option>
                        </select>
                        <select class="form-control" id="month1" style="display:none;" disabled>
                           <option value="01">January</option>
                        </select>
                        <select class="form-control" id="month2" style="display:none;"></select>
                        <select class="form-control" id="yearSelect"></select>
                        <button type="button" class="form-control" id="monthlybranchincomestatement_submit" name="monthlybranchincomestatement_submit" data-prefix="monthlybranchincomestatement">Search</button>
                   </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead class="monthlybranchincomestatement_report_header" style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                        </thead>
                        <tbody class="monthlybranchincomestatement_report">
                            <tr>
                                <td class="no-data"><center>- No Data Generated -</center></td>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="grossrevenuecontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" data-prefix="grossrevenue" required></select>
                        <select class="form-control region-select" data-prefix="grossrevenue" required></select>
                        <input type="date" class="form-control date_start" required/>
                        <input type="date" class="form-control date_end" required/>
                        <button type="button" class="form-control submit" id="grossrevenue_submit" name="grossrevenue_submit" data-prefix="grossrevenue">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th>Date</th>
                                <th>Zone</th> 
                                <th>Region</th> 
                                <th>Branch</th> 
                                <th>Revenue</th> 
                                <th>Cost of Sales</th> 
                                <th style="text-align-last: center;">Variance</th>
                            </tr>
                        </thead>
                        <tbody class="grossrevenue_report">
                            <tr>
                                <td colspan='5'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="incomesummarycontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" data-prefix="incomesummary" required></select>
                        <select class="form-control region-select" data-prefix="incomesummary" required></select>
<!--                        <select class="form-control branch-select" data-prefix="incomesummary" required></select>-->
                        <input type="date" class="form-control date_start" required/>
                        <input type="date" class="form-control date_end" required/>
                        <button type="button" class="form-control submit" id="incomesummary_submit" name="incomesummary_submit" data-prefix="incomesummary">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th colspan="4" id="zr">Zone / Region :</th>
                            </tr>
                            <tr>
                                <th>Date</th> 
                                <th>GLCode</th>  
                                <th>Description</th> 
                                <th>Amount</th> 
                            </tr>
                        </thead>
                        <tbody class="incomesummary_report">
                            <tr>
                                <td colspan='4'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="losingbranchescontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" data-prefix="losingbranches" required></select>
                        <select class="form-control region-select" data-prefix="losingbranches" required></select>
                        <select class="form-control branch-select" data-prefix="losingbranches" required></select>
                        <input type="date" class="form-control date_start" required/>
                        <button type="button" class="form-control submit" id="losingbranches_submit" name="losingbranches_submit" data-prefix="losingbranches">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th>Zone</th> 
                                <th>Region</th> 
                                <th>Branch</th> 
                                <th>Label</th> 
                                <th>Amount</th> 
                            </tr>
                        </thead>
                        <tbody class="losingbranches_report">
                            <tr>
                                <td colspan='5'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="orcashbalancecontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" data-prefix="orcashbalance" required></select>
                        <select class="form-control region-select" data-prefix="orcashbalance" required></select>
                        <input type="date" class="form-control date_start" required/>
                        <select class="form-control c1_select" required>
                            <option value="">Cash</option>
                            <option value="teller">Teller Cash</option>
                            <option value="vault">Vault Cash</option>
                        </select>
                        <select class="form-control c2_select" required>
                            <option value="">Currency</option>
                            <option value="php">PHP</option>
                            <option value="usd">USD</option>
                            <option value="eur">EUR</option>
                            <option value="jpy">JPY</option>
                        </select>
                        <button type="button" class="form-control submit" id="orcashbalance_submit" name="orcashbalance_submit" data-prefix="orcashbalance">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th>Date</th>
                                <th>Zone</th> 
                                <th>Region</th> 
                                <th>Amount</th> 
                            </tr>
                        </thead>
                        <tbody class="orcashbalance_report">
                            <tr>
                                <td colspan='4'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="orbankbalancecontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" data-prefix="orbankbalance" required></select>
                        <select class="form-control region-select" data-prefix="orbankbalance" required></select>
                        <input type="date" class="form-control date_start" required/>
                        <select class="form-control c1_select" required>
                            <option value="">Currency</option>
                            <option value="php">PHP</option>
                            <option value="usd">USD</option>
                        </select>
                        <button type="button" class="form-control submit" id="orbankbalance_submit" name="orbankbalance_submit" data-prefix="orbankbalance">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th>Date</th>
                                <th>Zone</th> 
                                <th>Region</th> 
                                <th>Amount</th> 
                            </tr>
                        </thead>
                        <tbody class="orbankbalance_report">
                            <tr>
                                <td colspan='4'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="orreceivablescontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control zone-select" data-prefix="orreceivables" required></select>
                        <select class="form-control region-select" data-prefix="orreceivables" required></select>
                        <input type="date" class="form-control date_start" required/>
                        <button type="button" class="form-control submit" id="orreceivables_submit" name="orreceivables_submit" data-prefix="orreceivables">Search</button>
                        <button type="button" class="btn btn-info form-control" id="excel" onclick="downloadExcel()"><span class="fa fa-file-excel-o"></span> Excel </button>
                    </div>
                </form>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                            <tr>
                                <th>Date</th>
                                <th>Zone</th> 
                                <th>Region</th> 
                                <th>Amount</th> 
                            </tr>
                        </thead>
                        <tbody class="orreceivables_report">
                            <tr>
                                <td colspan='4'><center>- No Data Generated -</center></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>