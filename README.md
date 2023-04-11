# ChatGPT Discord Bot

This is an open-source project for building a bot for Discord using the [Quarkus](https://quarkus.io/), the Supersonic Subatomic Java Framework.

## Getting Started

1. Obtain a Discord API key by creating a new bot account:

- Go to the [Discord Developer Portal](https://discord.com/developers/applications) and create a new app.
- Select "Bot" from the left-hand menu, click "Add Bot", and follow the instructions to create a bot account for your app.
- Copy your bot token, which will be used as your Discord API key.

2. Obtain an OpenAI API key:

- Sign up for a free account on the [OpenAI website](https://beta.openai.com/signup/).
- Create an API key by following the instructions in the [OpenAI API documentation](https://platform.openai.com/account/api-keys).

3. Save the API keys in the ./config/application.properties file:

```properties
openai.apiKey=OPENAI_API_KEY
discord.apiKey=DISCORD_API_KEY
```


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.
## Contributing

Contributions to this project are welcome. If you find a bug or have an idea for a new feature, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
