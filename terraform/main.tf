terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 0.14.9"
}

provider "aws" {
  profile                 = "default"
  region                  = "us-east-2"
  shared_credentials_file = "~/.aws/credentials-ynov"
}

data "template_file" "user_data" {
  template = file("add-ssh-web-app.yaml")
}

resource "aws_instance" "app_server" {
  ami                         = "ami-0d97ef13c06b05a19"
  instance_type               = "t2.micro"
  count                       = 1
  key_name                    = "ssh_MAILLER_cloud-init"
  associate_public_ip_address = "true"
  vpc_security_group_ids      = [aws_security_group.allow_SSH_cloud-init.id]
  user_data                   = data.template_file.user_data.rendered

  tags = {
    Name   = "${var.instance_name}-${count.index}"
    Groups = "app"
    Owner  = "MAILLER Corentin"
  }
}

resource "aws_key_pair" "deployer" {
  key_name   = "ssh_MAILLER_cloud-init"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDWJmJtkR8NdFrL4xcoL3OutpifmKrhxxIY6PJOGxTg1Ikdh5rCg4dUM3AaoLtQp95YMV0teU9H3rNpGC80e5KX6/EoYH7hQBzQG+kuNSKVxrjIk1Uk/B5tAX0vVoEE28JpSz+KQu1aGI2nYEES+Jgeq97K/s0f9xu+ZX8ucgNmwBLKZCTrM39B774Vc2YG8bMcZ/5X8hSJdSPoLRDsChx+oL4doSh+foKBMgZjw434KMQCrg9nrLypyTVvuru9q1EJtcgmSWC8AHWpS0YQx8XrielxFMNIaCjegzW+Ph8WU+7Wt3ds2/abScOW9r4HaBh5vBUQcQyzzPZFOShepV6aQ+T/+IlJToAsr5HxZUeiC4F8PLZp2Tp1zhNDcmLvNiKK70PRJ6L1S6w7Kyd9ZL29F2PQGpTxM+FyOmZW0mSyV+CKFCckpKIJz+akg1gPGCsP5O6J5WpjuFNWukZCymPHUGpfqvi+8X1NShd40gqPLnHda3qi2ssbZSbnZ+33mnE= maillerc@LAPTOP-7H61VPOA"
}

resource "aws_security_group" "allow_SSH_cloud-init" {
  name        = "allow_SSH_MAILLER_cloud-init"
  description = "Allow SSH inbound traffic"

  ingress {
    description      = "SSH from VPC"
    from_port        = 22
    to_port          = 8080
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name   = var.instance_name
    Groups = "app"
    Owner  = "MAILLER Corentin"
  }
}