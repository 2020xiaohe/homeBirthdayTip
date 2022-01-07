/**
 * @author hemb
 * @date 2020/12/13 15:30
 */
function addOperation(){
    var nextNodeIdDiv=$("#nextNodeIdDiv").html();
    var index=$("#listTable").find("tr").length;
    var tr=$("<tr class='tr'>");
    tr.append($("<td align='center'><a href='#'><i style=\"color: red\" class='icon ion-minus-round' onclick='removeOperation(this)'></i></a></td>"));
    tr.append($("<td><input type='hidden'  name='ids' value='' class='input_txt w99' ><input type='text'  name='name' value='' style='width:90%' class='input_txt w99' ></td><td><input type=\"text\"  name=\"oldBirthDay\" value='' style=\"width: 90%\" class=\"input_txt w99\" ></td>"));
    tr.append($("<td>"+nextNodeIdDiv+"</td>"));
    tr.append($("<td> <input type=\"text\"  name=\"phoneNumber\" value=\"\" style=\"width: 90%\" class=\"input_txt w99\" ></td>"));
    tr.append($("<td> <input type=\"text\"  name=\"wxOpenId\" value=\"\" style=\"width: 90%\" class=\"input_txt w99\" ></td>"));
    tr.append($("<td><input type='hidden'   name='seqs' value='"+index+"' class='input_txt w99' ><a style=\"color: blue;margin-left: 40%\"  onclick='moveUp(this)'><i class='icon ion-arrow-up-c'></i></a><a style=\"color: blue;margin-left: 10%\" onclick='moveDown(this)'><i  class='icon ion-arrow-down-c'></i></a></td>"));
    $("#listTable").append(tr);
}
function moveUp(obj){//上移
    var tr=$(obj).parent().parent();
    var index=tr.index();
    if(index>1){
        $(obj).parent().find("input[name='seqs']").val(tr.prev().index());
        tr.prev().find("input[name='seqs']").val(index);
        tr.prev().before(tr);
    }

}
function moveDown(obj){//下移
    var tr=$(obj).parent().parent();
    var index=tr.index();
    if(index<$("#listTable").find("tr").length){
        $(obj).parent().find("input[name='seqs']").val(tr.next().index());
        tr.next().find("input[name='seqs']").val(index);
        tr.next().after(tr);
    }
}
function removeOperation(obj){
    $(obj).parents("tr:eq(0)").remove();
}
function delOperat(selectId){
    if(confirm("确定要删除该条记录？")){
        $.ajax({
            url:"/delete",
            type:"get",
            data:{"id":selectId},
            datatype:"json",
           /* beforeSend: function () {
                ajaxLoading();
            },*/
            success:function(data,textStatus){
                alert("删除成功");
                $(obj).parents("tr:eq(0)").remove();
            },
            complete:function(XMLHttpRequest,textStatus){
                location.reload();
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){
                alert("删除失败");
            }
        });
    }
}
