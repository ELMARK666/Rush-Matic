<div class="peraccountscontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <form method="POST">
                <div class="form-popup" style="position: fixed; z-index: 10;">
                    <div class="form-row">
                        <input type="text" class="form-control" id="per_account_date_from" placeholder="Date from" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">
                        <input type="text" class="form-control" id="per_account_date_to" placeholder="Date to" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">

                        <select class="form-control filter_criteria" id="per_account_filter" style="width:220px">
                            <option value="">Filter Criteria</option>
                            <option value="1">Zone</option>
                            <option value="2">Region</option>
                            <option value="3">Branch</option>
                        </select>

                        <select class="form-control zone-select" id="per_account_zones" style="width:120px">
                            <option value="">Select Zone</option>
                        </select>
                        <select class="form-control region-select" id="per_account_region" style="width:220px">
                            <option value="">Select Region</option>
                        </select>
                        <input type="text" class="form-control" id="per_account_criteria_search" placeholder="Search Criteria" style="width:220px">
                        
                        <div style="position: relative; width: 220px;"> <!-- container to keep dropdown aligned -->
                            <input type="text" class="form-control" id="per_account_gl" 
                                   placeholder="GLCode" style="width:220px"
                                   onkeyup="showResult(this.value)">
                            <ul id="livesearch"></ul>
                        </div>
                        
                        <input type="hidden" id="action" value="filter_asset" />

                        <button type="submit" class="form-control" id="serach_peraccount_submit" style="padding:0px;">Search</button>
                        
                        <!--<input type="hidden" name="action" id="action" value="filter">-->
                        <!--<input type="submit" class="form-control btn btn-default" name="serach_peraccount_submit" id="serach_peraccount_submit" value="Search"/>-->
                    </div>
                </div>
            </form>
            <!-- Table always visible -->
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse;margin-top: 30px;">
                        <thead style="color: Black; text-align: center; position: sticky; top: 0; background-color: #fff; z-index: 1;">
                        <tr id="table-header">
                        </tr>
                        </thead>
                        <tbody id="ledger_per_accoount">
                            <tr><td colspan="5" style="text-align:center;">No data loaded yet</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="percategorycontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup" style="position: fixed; z-index: 10;">
                <div class="form-row">
                    <input type="text" class="form-control" id="per_categorys_date_gl_from" name="per_categorys_date_gl_from" placeholder="Date from" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">
                    <input type="text" class="form-control" id="per_categorys_date_gl_to" name="per_categorys_date_gl_to" placeholder="Date to" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">
                    
                    <select class="form-control range_criteria" id="per_categorys_filter" name="per_categorys_filter" style="width:220px">
                        <option value="">Filter Criteria</option>
                        <option value="1">Zone</option>
                        <option value="2">Region</option>
                        <option value="3">Branch</option>
                    </select>

                    <select class="form-control zone-select" id="per_categorys_zones" name="per_categorys_zones" style="width:120px">
                        <option value="">Select Zone</option>
                    </select>
                    <select class="form-control region-select" id="per_categorys_region" name="per_categorys_region" style="width:220px">
                        <option value="">Select Region</option>
                    </select>

                    <div style="position: relative; width: 220px;">
                        <input type="text" class="form-control" id="per_categorys_gl_from"
                               placeholder="GLCode" style="width:220px"
                               onkeyup="showResultCategorys(this.value)">
                        <ul id="per_categorys_livesearch"
                            style="position:absolute; width:100%; background:#fff; z-index:1000;
                                   list-style:none; padding:0; margin:0; border:1px solid #ccc; display:none;"></ul>
                    </div>


                    
                    <input type="text" class="form-control" id="per_categorys_category" name="per_categorys_category" placeholder="Category" style="width:220px">
                    <input type="text" class="form-control" id="per_categorys_branch" name="per_categorys_branch" placeholder="Branch Name" style="width:220px">

                    <input type="hidden" name="action" id="per_categorys_action" value="filter_range" />
                    <button type="button" class="form-control" id="per_categorys_generate_submit" name="per_categorys_generate_submit" data-prefix="generate" style="padding:0px;">Search</button>
                </div>
            </div>

            <div class="edi-logs">
                <br>
                <label></label>
                <table class="table table-bordered table-hover" id="per_categorys_generate_report" style="width:100%; border-collapse: collapse; margin-top: 10px;">
                    <thead style="color: Black; text-align: center;">
                        <tr id="per_categorys_table_header">
                            <th style="text-align-last: center;">GL Code</th>
                            <th style="text-align-last: center;">Description</th>
                            <th style="text-align-last: center;">Date</th>
                            <th style="text-align-last: center;">Region</th>
                            <th style="text-align-last: center;">Cost Center</th>
                            <th style="text-align-last: center;">Branches</th>
                            <th style="text-align-last: center;">Amount</th>
                        </tr>
                    </thead>
                    <tbody id="per_categorys_ledger"></tbody>
                    <tfoot>
                        <tr>
                            <td colspan="7" style="text-align:center;font-weight:bold;">No data available in table.</td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="perrangecontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup" style="position: fixed; z-index: 10;">
                <div class="form-row">
                    <input type="text" class="form-control" id="per_range_date_gl_from" name="per_cat_date_gl_from" placeholder="Date from" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">
                    <input type="text" class="form-control" id="per_range_date_gl_to" name="per_cat_date_gl_to" placeholder="Date to" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">
                    <select class="form-control criteria" id="per_range_criteria" name="per_cat_criteria" style="width:220px">
                        <option value="">Filter Criteria</option>
                        <option value="1">Zone</option>
                        <option value="2">Region</option>
                        <option value="3">Branch</option>
                    </select>
                    <select class="form-control zone-select" id="per_range_zones" name="per_cat_zones" style="width:120px">
                        <option value="">Select Zone</option>
                    </select>
                    <select class="form-control region-select" id="per_range_region" name="per_cat_region" style="width:220px">
                        <option value="">Select Region</option>
                    </select>
                    
                    <input type="text" class="form-control" id="per_range_gl_from_new" name="per_cat_gl_from_new" placeholder="GLCode from" style="width:220px">
                    <input type="text" class="form-control" id="per_range_gl_to" name="per_cat_gl_to" placeholder="GLCode to" style="width:220px">
                    
                    
                    
                    <input type="text" class="form-control" id="per_range_search" name="per_cat_search" placeholder="Search Branch" style="width:220px">
                    
                    <!--<input type="text" class="form-control" id="per_cat_category" name="per_cat_category" placeholder="Search Category" style="width:220px">-->
                    
                    <input type="hidden" name="action" id="action" value="filter_perrange" />
                    <button type="submit" class="form-control" id="serach_perrange_submit" style="padding:0px;">Search</button>
                    <button type="button" class="btn btn-info form-control hidden" id="excel" onclick="downloadExcel()">
                        <span class="fa fa-file-excel-o"></span> Excel
                    </button>
                </div>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse; margin-top:30px;">
                        <thead style="color:black; text-align:center; position:sticky; top:0; background:#fff;">
                            <tr id="table-header"></tr>
                        </thead>
                        <tbody id="ledger_per_range">
                            <tr><td colspan="5" style="text-align:center;">No data loaded yet</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="checkglbalancecontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup" style="position: fixed; z-index: 10;">
                <div class="form-row">
                    <input type="text" class="form-control" id="per_check_date_gl_from" name="per_check_date_gl_from" placeholder="Date" onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'" style="width:120px">
                    <select class="form-control zone-select" id="per_check_zones" name="per_check_zones" style="width:120px">
                        <option value="">Select Zone</option>
                    </select>
                    <select class="form-control region-select" id="per_check_region" name="per_check_region" style="width:220px">
                        <option value="">Select Region</option>
                    </select>
                    
                    <input type="text" class="form-control" id="per_check_gl_from_new" name="per_check_gl_from_new" placeholder="GLCode from" style="width:220px">
                    <input type="text" class="form-control" id="per_check_gl_to" name="per_check_gl_to" placeholder="GLCode to" style="width:220px">
                    
                    
                    <input type="hidden" name="action" id="action" value="filter_check" />
                    <button type="button"
                            class="form-control"
                            id="serach_check_submit"
                            style="padding:0px;width:200px;">
                        Search
                    </button>
                </div>
            </div>
            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover" style="width:100%; border-collapse: collapse; margin-top:30px;">
                        <thead style="color:black; text-align:center; position:sticky; top:0; background:#fff;">
                            <tr id="table-header">
                                <th>Branch Code</th>
                                <th>Branch</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody id="ledger_per_check">
                            <tr><td colspan="5" style="text-align:center;">No data loaded yet</td></tr>
                        </tbody>
                    </table>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-md-6 form-group">
                        </div>
                        <div class="col-md-6 form-group">
                            <table class="table table-responsive table-borderless" width="100%">
                                <tbody id="checkgl_total"></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="hocurrentcontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup" style="position: fixed; z-index: 10;">
                <div class="form-row">
                    <input type="text"class="form-control"id="ho_current_date_gl_from"name="ho_current_date_gl_from"placeholder="Date From"onfocus="(this.type='date')"onblur="if(!this.value)this.type='text'"style="width:120px">
                    <input type="text"class="form-control"id="ho_current_date_gl_to"name="ho_current_date_gl_to"placeholder="Date To"onfocus="(this.type='date')"onblur="if(!this.value)this.type='text'"style="width:120px">
                    <select class="form-control zone-select"id="ho_current_zones"name="ho_current_zones"style="width:120px">
                        <option value="">Select Zone</option>
                    </select>
                    
                    <select class="form-control region-select"id="ho_current_region"name="ho_current_region"style="width:220px">
                        <option value="">Select Region</option>
                    </select>
                    
                    <select class="form-control" id="ho_current_criteria"name="ho_current_region"style="width:220px">
                        <option value="">Select Criteria</option>
                        <option value="Branch">Branch Name</option>
                        <option value="Desc">Description</option>
                    </select>
                    
                    <input type="text"class="form-control"id="ho_current_gl"name="ho_current_gl"placeholder="GLCode to"style="width:220px">
                    
                    <select class="form-control"id="ho_current_category"name="ho_current_region"style="width:220px">
                        <option value="ALL">All Category</option>
                        <option value="Acquisition">Acquisition</option>
                       <!--<option value="Adjustment">Adjustment</option>-->
                        <option value="Allocation">Allocation</option>
                        <option value="Branch Expense">Branch Expense</option>
                        <option value="CRRF">CRRF</option>
                        <option value="HO Data">HO Data</option>
                        <option value="Payremit">Payremit</option>
                        <option value="PAYROLL 15">Payroll 15</option>
                        <option value="PAYROLL 30">Payroll 30</option>
                        <!--<option value="Provision">Provision</option>-->
                        <option value="Smart">Smart</option>
                        <option value="SSMI">SSMI</option>
                        <option value="SG">SG</option>
                    </select>
                    <input type="hidden"name="action"id="action"value="filter_ho_current" />
                    <button type="button"class="form-control"id="search_ho_current_submit"style="padding:0px;width:200px;">Search</button>
                </div>
            </div>

            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover"
                           style="width:100%; border-collapse: collapse; margin-top:30px;">
                        <thead style="color:black; text-align:center; position:sticky; top:0; background:#fff;">
                            <tr id="table-header">
                                <th>Branch Code</th>
                                <th>Branch</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody id="ledger_ho_current">
                            <tr>
                                <td colspan="5" style="text-align:center;">
                                    No data loaded yet
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="allocatedexpensescontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup" style="position: fixed; z-index: 10;">
                <div class="form-row">
                    <input type="text"
                           class="form-control"
                           id="allocated_date_from"
                           name="allocated_date_from"
                           placeholder="Date From"
                           onfocus="(this.type='date')"
                           onblur="if(!this.value)this.type='text'"
                           style="width:120px">

                    <input type="text"
                           class="form-control"
                           id="allocated_date_to"
                           name="allocated_date_to"
                           placeholder="Date To"
                           onfocus="(this.type='date')"
                           onblur="if(!this.value)this.type='text'"
                           style="width:120px">

                    <select class="form-control zone-select"
                            id="allocated_zones"
                            name="allocated_zones"
                            style="width:120px">
                        <option value="">Select Zone</option>
                    </select>

                    <select class="form-control region-select"
                            id="allocated_region"
                            name="allocated_region"
                            style="width:220px">
                        <option value="">Select Region</option>
                    </select>
                    
                    <input type="hidden" name="action" id="action_allocated" value="filter_allocated" />

                    <button type="button"
                            class="form-control"
                            id="search_allocated_submit"
                            style="padding:0px;width:200px;">
                        Search
                    </button>
                </div>
            </div>

            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover"
                           style="width:100%; border-collapse: collapse; margin-top:30px;">
                        <thead style="color:black; text-align:center; position:sticky; top:0; background:#fff;">
                            <tr id="allocated_table_header">
                                <th>Branch Code</th>
                                <th>Branch</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody id="ledger_allocated">
                            <tr>
                                <td colspan="5" style="text-align:center;">No data loaded yet</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>
<div class="glcheckingcontainer" style="display:none;">
    <br>
    <div style="display: flex; gap: 20px; padding: 0 20px;">
        <div style="flex: 1;">
            <div class="form-popup" style="position: fixed; z-index: 10;">
                <div class="form-row">
                    <input type="text"
                           class="form-control"
                           id="gl_check_date_from"
                           name="gl_check_date_from"
                           placeholder="Date From"
                           onfocus="(this.type='date')"
                           onblur="if(!this.value)this.type='text'"
                           style="width:120px">

                    <input type="text"
                           class="form-control"
                           id="gl_check_date_to"
                           name="gl_check_date_to"
                           placeholder="Date To"
                           onfocus="(this.type='date')"
                           onblur="if(!this.value)this.type='text'"
                           style="width:120px">

                    <select class="form-control zone-select"
                            id="gl_check_zones"
                            name="gl_check_zones"
                            style="width:120px">
                        <option value="">Select Zone</option>
                    </select>

                    <select class="form-control region-select"
                            id="gl_check_region"
                            name="gl_check_region"
                            style="width:220px">
                        <option value="">Select Region</option>
                    </select>

                    <input type="text"
                           class="form-control"
                           id="gl_check_gl_from"
                           name="gl_check_gl_from"
                           placeholder="GLCode From"
                           style="width:220px">

                    <input type="text"
                           class="form-control"
                           id="gl_check_gl_to"
                           name="gl_check_gl_to"
                           placeholder="GLCode To"
                           style="width:220px">

                    <input type="hidden" name="action" id="action_gl_check" value="filter_gl_check" />

                    <button type="button"
                            class="form-control"
                            id="search_gl_check_submit"
                            style="padding:0px;width:200px;">
                        Search
                    </button>
                </div>
            </div>

            <div class="edi-logs" style="max-height: 700px;">
                <br><br>
                <div style="overflow-y: auto; max-height: 700px;">
                    <table class="table table-bordered table-hover"
                           style="width:100%; border-collapse: collapse; margin-top:30px;">
                        <thead style="color:black; text-align:center; position:sticky; top:0; background:#fff;">
                            <tr id="gl_check_table_header">
                                <th>Branch Code</th>
                                <th>Branch</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody id="ledger_gl_check">
                            <tr>
                                <td colspan="5" style="text-align:center;">No data loaded yet</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>
