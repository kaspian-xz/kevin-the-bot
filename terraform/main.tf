terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region = "eu-central-1"
}

resource "aws_instance" "kevin-instance" {
  ami           = "ami-0b9094fa2b07038b8" # Replace with your desired AMI ID
  instance_type = "t2.micro"              # Change instance type as needed
  key_name      = var.aws_key_name        # Replace with your SSH key pair name

  security_groups = ["network-security-group"] # Reference to the security group created below

  tags = {
    Name = "kevin-the-bot"
  }

  provisioner "remote-exec" {
    inline = [
      "sudo yum update -y",               # Update the package repository
      "sudo yum install docker -y",       # Install Docker on Amazon Linux
      "sudo service docker start",        # Start the Docker service
      "sudo usermod -aG docker ec2-user", # Add the ec2-user to the docker group
      "sudo docker run -d --restart=always -e discord.apiKey=\"${var.discord_api_key}\" -e openai.apiKey=\"${var.openai_api_key}\" -e openai.model=\"${var.openai_api_model}\" -e openai.timeout=\"${var.openai_api_timeout}\" kaspian/kevin-the-bot:latest",
    ]

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("${var.ssh_private_key_path}")
      host        = self.public_ip
    }
  }
}

resource "aws_security_group" "network-security-group" {
  name        = "network-security-group"
  description = "Allow SSH inbound and all outbound traffic"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Allow SSH from anywhere (In a production environment, restrict this to your IP or a more limited range)
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Allow all outbound traffic
  }
}

output "external_ip" {
  value = aws_instance.kevin-instance.public_ip
}
