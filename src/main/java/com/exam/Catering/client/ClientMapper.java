package com.exam.Catering.client;

public class ClientMapper {

    public static Client toClient(ClientDto clientDto) {

        Client client = new Client();
        client.setName(clientDto.getName());
//        client.setOrderings(clientDto.getOrderings());

        return client;
    }

    public static ClientDto toClientDto(Client client){

        ClientDto clientDto = new ClientDto();
        clientDto.setName(client.getName());
//        clientDto.setOrderings(client.getOrderings());

        return clientDto;
    }
}
