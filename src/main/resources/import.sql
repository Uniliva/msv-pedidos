INSERT INTO TB_PRODUTOS (COD_PRODUTO,DESCRICAO,QUANTIDADE_ESTOQUE,UNIDADE_MEDIDA,PRECO) VALUES(1,'Geladeira Eletrolux', 10.0, 'PC', 1252.5); 
INSERT INTO TB_PRODUTOS (COD_PRODUTO,DESCRICAO,QUANTIDADE_ESTOQUE,UNIDADE_MEDIDA,PRECO) VALUES(2,'TV SANSUNG', 8.0, 'PC', 2252.5);
INSERT INTO TB_PRODUTOS (COD_PRODUTO,DESCRICAO,QUANTIDADE_ESTOQUE,UNIDADE_MEDIDA,PRECO) VALUES(3,'NOTEBOOK DELL', 3.0, 'PC', 4252.5);
INSERT INTO TB_PRODUTOS (COD_PRODUTO,DESCRICAO,QUANTIDADE_ESTOQUE,UNIDADE_MEDIDA,PRECO) VALUES(4,'CURSO DE JAVA', 20.0, 'VAGAS', 120.5);


INSERT INTO TB_PEDIDO (COD_PEDIDO,DATA_PEDIDO,QUANTIDADE_PRODUTOS) VALUES(1,'2019-02-10', 1.0); 
INSERT INTO TB_PEDIDO (COD_PEDIDO,DATA_PEDIDO,QUANTIDADE_PRODUTOS) VALUES(2,'2019-02-11', 2.0); 
INSERT INTO TB_PEDIDO (COD_PEDIDO,DATA_PEDIDO,QUANTIDADE_PRODUTOS) VALUES(3,'2019-02-12', 3.0); 
INSERT INTO TB_PEDIDO (COD_PEDIDO,DATA_PEDIDO,QUANTIDADE_PRODUTOS) VALUES(4,'2019-02-13', 1.0); 


INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(1,2);
INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(2,1);
INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(2,3);
INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(3,1);
INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(3,4);
INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(3,3);
INSERT INTO TB_ITENS_PEDIDO (COD_PEDIDO,COD_PRODUTO) VALUES(4,4); 
