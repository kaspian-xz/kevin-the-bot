# Terraform AWS EC2 Deployment with Docker

This Terraform script automates the deployment of an Amazon Web Services (AWS) EC2 instance, installs Docker on it, and runs the "kevin-the-bot" Docker image. The script uses environment variables for configuration, making it easy to customize the deployment.

## Prerequisites

Before you begin, make sure you have the following prerequisites in place:

1. [Terraform](https://learn.hashicorp.com/tutorials/terraform/install-cli) installed on your local machine.
2. An AWS key pair created for SSH access. If you don't have a key pair, follow the [AWS key pair setup instructions](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-key-pairs.html#create-key-pair) to create one.

## Configuration

Before running the Terraform script, you need to create a `terraform.tfvars` file in the same directory as your script. Define the following variables in the `terraform.tfvars` file with your actual API keys and SSH key information:

```hcl
discord_api_key      = "YOUR_DISCORD_API_KEY"
ssh_private_key_path = "PATH_TO_YOUR_LOCAL_SSH_PRIVATE_KEY"
aws_key_name         = "YOUR_AWS_KEYPAIRNAME"
openai_api_key       = "YOUR_OPENAI_API_KEY"
openai_api_timeout   = "60"
openai_api_model     = "gpt-3.5-turbo"
```

Replace `"YOUR_OPENAI_API_KEY"`, `"YOUR_DISCORD_API_KEY"`, `"PATH_TO_YOUR_LOCAL_SSH_PRIVATE_KEY"`, and `"YOUR_AWS_KEYPAIRNAME"` with your respective values.

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

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
```

The license section now specifies that the project is licensed under the MIT License. Make sure to include a `LICENSE` file in your project directory with the MIT License terms.