<%@ tag trimDirectiveWhitespaces="true" %>
<%@ tag description="adminSearch.jsp - input panel" pageEncoding="UTF-8" %>
<%@ tag import="teammates.common.util.Const" %>
<%@ attribute name="searchKey" required="true" %>

<div class="well well-plain">
  <form class="form-horizontal" method="get" action="" id="activityLogFilter" role="form">
    <div class="panel-heading" id="filterForm">
      <div class="form-group">
        <div class="row">
          <div class="col-md-12">
            <div class="alert alert-success" role="alert">
              <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
              <span class="sr-only">Tips:</span>
              Surround key word to search a whole string or string contains punctuation like "-" "."
            </div>

            <div class="col-md-4">
              <h3 class="text-right">Search as a administrator:  </h3>
            </div>
            <div class="col-md-8">
              <div class="input-group input-group-lg">
                <input type="text" class="form-control" id="filterQuery"
                       name="<%=Const.ParamsNames.ADMIN_SEARCH_KEY%>"
                       value="${searchKey}">

                <span class="input-group-btn">
                  <button class="btn btn-default" type="submit"
                          name="<%=Const.ParamsNames.ADMIN_SEARCH_BUTTON_HIT%>"
                          id="searchButton" value="true">Search</button>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </form>
</div>
