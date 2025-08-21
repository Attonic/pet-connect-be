{ pkgs, ... }: {
  channel = "stable-24.05";

  # Pacotes para o ambiente Java + Docker
  packages = [
    pkgs.jdk         # Kit de Desenvolvimento Java
    pkgs.maven       # Ferramenta de build para o projeto Spring Boot
    pkgs.docker
    pkgs.docker-compose
  ];

  # Habilita o serviço do Docker
  services.docker = {
    enable = true;
  };

  # Configurações do ambiente IDX
  idx = {
    extensions = [
      "vscjava.vscode-java-pack" # Pacote essencial de extensões para Java
    ];

    # # Configuração de preview para a aplicação Spring Boot
    # previews = {
    #   enable = true;
    #   previews = {
    #     web = {
    #       cwd = "backend";
    #       command = ["./mvnw" "spring-boot:run"];
    #       manager = "web";
    #     };
    #   };
    # };
  };
}