<#include '/common/header.ftl'>
<#include '/common/nav.ftl'>
<div class="container">
    <table class="table" id="App"></table>
</div>
<button type="button" class="btn btn-primary hidden" data-toggle="modal" id="tips-modal-btn"data-target="#tipsModal">
</button>
    <div class="modal fade" id="tipsModal" tabindex="-1" role="dialog"  aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body modal-tips">
          </div>
        </div>
      </div>
    </div>
  </div>
<script src="/js/crsf_ajax.js"></script>
<script src="/js/reminder-list-opt.js"></script>
<script src="/js/reminder-list.js"></script>
<#include '/common/footer.ftl'>