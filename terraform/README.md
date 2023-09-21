# Terraform AWS EC2 Deployment with Docker

This Terraform script automates the deployment of an Amazon Web Services (AWS) EC2 instance, installs Docker on it, and runs the "kevin-the-bot" Docker image. The script uses environment variables for configuration, making it easy to customize the deployment.

## Configuration

Before running the Terraform script, you need to create a `terraform.tfvars` file in the same directory as your script. Define the following variables in the `terraform.tfvars` file with your actual API keys:

```hcl
openai_api_key      = "YOUR_OPENAI_API_KEY"
openai_api_timeout  = "60"
openai_api_model    = "gpt-3.5-turbo"
discord_api_key     = "YOUR_DISCORD_API_KEY"
```

Replace `"YOUR_OPENAI_API_KEY"` and `"YOUR_DISCORD_API_KEY"` with your respective API keys.

## Usage

1. Initialize the Terraform workspace:

   ```shell
   terraform init
   ```

2. Deploy the infrastructure:

   ```shell
   terraform apply
   ```

   Terraform will create the EC2 instance, install Docker, and start the "kevin-the-bot" Docker container with the provided environment variables.

3. Access the External IP

   Once the deployment is complete, you can find the external IP address of the EC2 instance in the Terraform output:

   ```shell
   terraform output external_ip
   ```

   You can use this IP address to access the deployed service.

## Cleanup

When you're done using the deployed resources, you can remove them to avoid incurring further charges:

```shell
terraform destroy
```

This command will destroy all the resources created by the Terraform script.

## Disclaimer

- Be cautious with your API keys and sensitive information. Do not expose them in public repositories or to unauthorized users.
- This script is a basic example and may require modifications to suit your specific use case and security requirements.

