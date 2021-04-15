var idatual = 0;
var modalCadastro;
var modalAlerta;
var modalExcluir;

window.onload = function(e) {
  listar();
}

function listar() {
  var tab = document.getElementById("tabela");
  for (var i = tab.rows.length -1; i > 0; i--) {
    tab.deleteRow(i);
  }

  fetch("http://localhost:8080/CrudRestFull/filme", {method: "GET"})
  .then(response => response.json())
  .then(data => {
    for (const item of data ) {
      var tab = document.getElementById("tabela");
      var row = tab.insertRow(-1);
      row.insertCell(-1).innerHTML = item.id_filme;
      row.insertCell(-1).innerHTML = item.nome;
      row.insertCell(-1).innerHTML = item.autor;
      row.insertCell(-1).innerHTML = item.editora;
      row.insertCell(-1).innerHTML = item.ano_lancamento;
      row.insertCell(-1).innerHTML = "<button type='button' class='btn btn-primary' "
      + " onclick='alterar("+item.id_filme+")'> "
      + " <i class='bi bi-pencil'></i></button>"
      + " <button type='button' class='btn btn-danger' "
      + " onclick='excluir("+item.id_filme+")'> "
      + " <i class='bi bi-trash'></i></button>"
    }
  })
  .catch(error => console.log("Erro: " + error));
}

function novo() {
  idatual = 0;
  document.getElementById("txtNome").value = "";
  document.getElementById("txtAutor").value = "";
  document.getElementById("txtEditora").value = "";
  document.getElementById("txtLancamento").value = "";

  modalCadastro = new bootstrap.Modal(document.getElementById("modalCadastro"));
  modalCadastro.show();
}

function alterar(id) {

  idatual = id;
  modalCadastro = new bootstrap.Modal(document.getElementById("modalCadastro"));
  modalCadastro.show();

  fetch("http://localhost:8080/CrudRestFull/filme/" + idatual, {method: "GET"})
    .then(response => response.json())
    .then(data => {
      document.getElementById("txtNome").value = data.nome;
      document.getElementById("txtAutor").value = data.autor;
      document.getElementById("txtEditora").value = data.editora;
      document.getElementById("txtLancamento").value = data.ano_lancamento;
    })
    .catch(error => console.log('error', error));
}

function excluir(id) {
  idatual = id;
  document.getElementById("modalAlertaBody").style.backgroundColor = "#E0F2F1";
  document.getElementById("modalAlertaBody").innerHTML = "<h5>Confirma a exclusão?</5>"
  +  "<button type='button' class='btn btn-primary' onclick='excluirSim()'>Sim</botton>"
  +  "<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>Não</botton>"
  modalExcluir = new bootstrap.Modal(document.getElementById("modalAlerta"));
  modalExcluir.show();
}

function excluirSim() {
  fetch("http://localhost:8080/CrudRestFull/filme/"+idatual, {method: "DELETE"})
    .then(response => response.json())
    .then(result => {

      modalExcluir.hide()
      listar()

      if (result.success) {
        mostrarAlerta("Registro excluido com sucesso!", true)
      }
      else {
        mostrarAlerta("Falha ao deletar o registro!", false)
      }

    })
    .catch(error => console.log('error', error));
}

function salvar() {
  var f = {
    id_filme: idatual,
    nome: document.getElementById("txtNome").value,
    autor: document.getElementById("txtAutor").value,
    editora: document.getElementById("txtEditora").value,
    ano_lancamento: document.getElementById("txtLancamento").value
  }

  var json = JSON.stringify(f);

  var url;
  var metodo;

  if(idatual == 0) {
    url = "http://localhost:8080/CrudRestFull/filme";
    metodo = "POST";
  }
  else {
    url = "http://localhost:8080/CrudRestFull/filme/" + idatual;
    metodo = "PUT";
  }

  fetch(url, {method: metodo, body: json})
  .then(response => response.json())
  .then(result => {
    if (result.success) {
      mostrarAlerta(result.message, true);
      modalCadastro.hide();
      listar();
    }
    else {
      mostrarAlerta(result.message, false);
    }
  });
}

function mostrarAlerta(msg, success) {
  if (success) {
    document.getElementById("modalAlertaBody").style.backgroundColor = "#E0F2F1";
  }
  else {
    document.getElementById("modalAlertaBody").style.backgroundColor = "#FFEBEE";
  }
  document.getElementById("modalAlertaBody").innerHTML = msg;
  modalAlerta = new bootstrap.Modal(document.getElementById("modalAlerta"));
  modalAlerta.show();
  window.setTimeout(function(){
    modalAlerta.hide();
  }, 3000);
}