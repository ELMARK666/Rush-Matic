<div class="narrativereportcontainer income-container" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup">
                <form method="POST" enctype="multipart/form-data" autocomplete="off" >
                    <div class="form-row">
                        <select class="form-control group-select" name="narrativereport_group" id="narrativereport_group" data-prefix="narrativereport" required>
                            <option value="">-Group-</option>
                            <option value="Region">Region</option>
                            <option value="Branch">Branch</option>
                            <option value="Consolidated">Consolidated</option>
                        </select>
                        <select class="form-control zone-select" id="narrativereport_zones" name="narrativereport_zones" data-prefix="narrativereport" required></select>
                        <select class="form-control region-select" id="narrativereport_region" name="narrativereport_region" data-prefix="narrativereport" required></select>
                        <select class="form-control branch-select" id="narrativereport_branch" name="narrativereport_branch" data-prefix="narrativereport" required></select>
                        <select class="form-control date_start" id="narrativereport_month1" name="narrativereport_month1" required></select>
                        <select class="form-control year_select" id="narrativereport_year" name="narrativereport_year" required>
                            <option value="">YYYY</option>
                        </select>
                        <button type="button" class="form-control" id="narrativereport_submit" name="narrativereport_submit" data-prefix="narrativereport">Search</button>
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
                        <tbody class="narrativereport_report">
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